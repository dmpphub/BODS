package com.dataprocess.bods.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.dataprocess.bods.dao.ConfiguratorDAO;
import com.dataprocess.bods.entity.ConfiguratorEO;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.util.BytesUtil;
import com.dataprocess.bods.util.connectionutil.TargetSchemaConnection;
import com.dataprocess.bods.vo.ConfiguratorColumnDefinitionVO;
import com.dataprocess.bods.vo.ConfiguratorVO;
import com.dataprocess.bods.vo.ConfiguratorValidationVO;

/**
 * The Class ConfiguratorExecutor.
 */
public final class ConfiguratorExecutor {

    /**
     * Loader staging table insert.
     *
     * @param configuratorId the configurator id
     * @param configuratorConnId the configurator conn id
     * @throws BODSException the bODS exception
     */
    public void loaderStagingTableInsert(int configuratorId, int configuratorConnId) throws BODSException {
        int totalStgRecord = 0;
        String stagingTableName = "";
        String prevalidationTableName = "";
        ConfiguratorVO configuratorVO = null;
        ConfiguratorEO configuratorEO = null;
        ConfiguratorDAO configuratorDAO = null;
        Connection connection = null;
        Connection sourceConnection = null;
        BytesUtil bytesUtil = null;
        TargetSchemaConnection targetSchemaConnection = null;
        try {
            configuratorDAO = new ConfiguratorDAO();
            configuratorVO = new ConfiguratorVO();
            bytesUtil = new BytesUtil();
            targetSchemaConnection = new TargetSchemaConnection();
            connection = targetSchemaConnection.getTargetSchemaConnection(configuratorConnId);
            configuratorEO = configuratorDAO.fetchConfigurator(configuratorId);
            configuratorVO =
                (ConfiguratorVO) bytesUtil.toObject(configuratorEO.getConfiguratorBinariesEOSet().iterator().next()
                    .getObject());
            stagingTableName = "STG_" + configuratorVO.getConfiguratorConnectionId() + "_" + configuratorId;
            prevalidationTableName = "PV_" + configuratorVO.getConfiguratorConnectionId() + "_" + configuratorId;
            configuratorDAO.deleteStagingTableRecords(stagingTableName, connection);
            configuratorDAO.updatePrevalidationCountForNextExecution(prevalidationTableName, connection);
            sourceConnection =
                targetSchemaConnection.getTargetSchemaConnection(configuratorVO.getSourceConfigurationConnectionId());
            totalStgRecord =
                loadRecordsIntoStagingTable(stagingTableName, configuratorVO, connection, sourceConnection);
            callPrevalidationProcedure(totalStgRecord, configuratorVO, connection, stagingTableName,
                prevalidationTableName);
            configuratorDAO.updateStatusCodeBlock(sourceConnection, stagingTableName, configuratorVO);
            configuratorDAO.buildStagingDCFlagStatus(connection, 0, stagingTableName);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorExecutor", "loaderStagingTableInsert", exception.getMessage());
        } finally {
            targetSchemaConnection.targetSchemaClose(connection, null, null, null);
            targetSchemaConnection.targetSchemaClose(sourceConnection, null, null, null);
        }
    }

    /**
     * Call prevalidation procedure.
     *
     * @param totalStgRecord the total stg record
     * @param configuratorVO the configurator vo
     * @param connection the connection
     * @param stagingTableName the staging table name
     * @param prevalidationTableName the prevalidation table name
     * @throws BODSException the bODS exception
     */
    private void callPrevalidationProcedure(int totalStgRecord, ConfiguratorVO configuratorVO, Connection connection,
        String stagingTableName, String prevalidationTableName) throws BODSException {
        int prevalidationIndex = 0;
        int loaderExecutionId = 0;
        String procedure = "";
        PreparedStatement preparedStatement = null;
        try {
            for (ConfiguratorValidationVO configuratorValidationVO : configuratorVO.getConfiguratorValidationVOList()) {
                prevalidationIndex++;
                procedure = configuratorValidationVO.getValidationProcedureValue();
                if (procedure.indexOf("##STAGING_TABLE##") != -1) {
                    procedure = procedure.replaceAll("##STAGING_TABLE##", stagingTableName);
                }
                if (procedure.indexOf("##PV_STAGING_TABLE##") != -1) {
                    procedure = procedure.replaceAll("##PV_STAGING_TABLE##", prevalidationTableName);
                }
                if (procedure.indexOf("##TOTAL_COUNT##") != -1) {
                    procedure = procedure.replaceAll("##TOTAL_COUNT##", String.valueOf(totalStgRecord));
                }
                if (procedure.indexOf("##EXECUTION_ID##") != -1) {
                    procedure = procedure.replaceAll("##EXECUTION_ID##", String.valueOf(loaderExecutionId));
                }
                if (procedure.indexOf("##PV_COLUMN_SEQNO##") != -1) {
                    procedure = procedure.replaceAll("##PV_COLUMN_SEQNO##", "PV_" + prevalidationIndex);
                }
                System.out.println("After Replace Value :\n" + procedure);
                preparedStatement = connection.prepareCall(procedure);
                preparedStatement.executeQuery();
                preparedStatement.close();
            }
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorExecutor", "callPrevalidationProcedure", exception.getMessage());
        }
    }

    /**
     * Call staging procedure.
     *
     * @param procedure the procedure
     * @param connection the connection
     * @param stagingTableName the staging table name
     * @throws BODSException the bODS exception
     */
    private void callStagingProcedure(String procedure, Connection connection, String stagingTableName)
        throws BODSException {
        PreparedStatement preparedStatement = null;
        try {
            if (procedure.indexOf("##stagingTableName##") != -1) {
                procedure = procedure.replaceAll("##stagingTableName##", stagingTableName);
            }
            preparedStatement = connection.prepareCall(procedure);
            preparedStatement.executeQuery();
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorExecutor", "callStagingProcedure", exception.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Load records into staging table.
     *
     * @param stagingTableName the staging table name
     * @param configuratorVO the configurator vo
     * @param connection the connection
     * @param sourceConnection the source connection
     * @return the int
     * @throws BODSException the bODS exception
     */
    private int loadRecordsIntoStagingTable(String stagingTableName, ConfiguratorVO configuratorVO,
        Connection connection, Connection sourceConnection) throws BODSException {
        int rowCount = 0;
        int configuratorId = 0;
        StringBuffer sourceQuery = new StringBuffer();
        String[] destColumnNames = null;
        TargetSchemaConnection targetSchemaConnection = null;
        List<ConfiguratorColumnDefinitionVO> columnDefinitionVOList = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            targetSchemaConnection = new TargetSchemaConnection();
            resultSet = getExtractResultSet(configuratorVO, sourceConnection);
            columnDefinitionVOList = configuratorVO.getConfiguratorColumnDefinitionVOList();
            configuratorId = configuratorVO.getConfiguratorId();
            destColumnNames = getDestinationMappingColumnDetails(columnDefinitionVOList);
            sourceQuery.append(" INSERT INTO  ");
            sourceQuery.append(stagingTableName);
            sourceQuery.append(" (" + destColumnNames[1]
                + " , LINE_NO, EXECUTION_ID, CONFIGURATOR_ID, STATUS_CODE, DC_FLAG " + ") ");
            sourceQuery.append(" VALUES (" + destColumnNames[0] + " ,?, ?, ?, ?, ? " + ") ");
            preparedStatement = connection.prepareStatement(sourceQuery.toString());
            while (resultSet.next()) {
                rowCount = loadRecords(rowCount, configuratorId, columnDefinitionVOList, resultSet, preparedStatement);
            }
            if (rowCount % 50 != 0) {
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorExecutor", "loadRecordsIntoStagingTable", exception.getMessage());
        } finally {
            targetSchemaConnection.targetSchemaClose(null, preparedStatement, resultSet, null);
            targetSchemaConnection.targetSchemaClose(null, preparedStatement, resultSet, null);
        }
        return rowCount;

    }

    /**
     * Gets the extract result set.
     *
     * @param configuratorVO the configurator vo
     * @param sourceConnection the source connection
     * @return the extract result set
     */
    private ResultSet getExtractResultSet(ConfiguratorVO configuratorVO, Connection sourceConnection) {
        String sourceQuery = "";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ConfiguratorDAO configuratorDAO = null;
        try {
            configuratorDAO = new ConfiguratorDAO();
            sourceQuery = configuratorDAO.getExtractQueryFromDB(configuratorVO.getSourceConfigurationId());
            sourceQuery = sourceQuery.replaceAll(";", "");
            preparedStatement = sourceConnection.prepareStatement(sourceQuery);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            /*
             * if (preparedStatement != null) { try { preparedStatement.close(); } catch (SQLException e) {
             * e.printStackTrace(); } }
             */
        }
        return resultSet;
    }

    /**
     * Gets the destination mapping column details.
     *
     * @param columnDefinitionVOList the column definition vo list
     * @return the destination mapping column details
     * @throws BODSException the bODS exception
     */
    private String[] getDestinationMappingColumnDetails(List<ConfiguratorColumnDefinitionVO> columnDefinitionVOList)
        throws BODSException {
        int count = 0;
        String columns[] = new String[2];
        StringBuffer destinationSB = new StringBuffer();
        StringBuffer sourceSB = new StringBuffer();
        try {
            for (ConfiguratorColumnDefinitionVO columnMapping : columnDefinitionVOList) {
                if (columnMapping != null/* && "Y".equalsIgnoreCase(columnMapping.getActiveColumnFlag()) */) {
                    if (count != 0) {
                        destinationSB.append(",");
                        sourceSB.append(",");
                    }
                    count++;
                    destinationSB.append(columnMapping.getColumnName());
                    sourceSB.append("?");
                }
            }
            columns[0] = sourceSB.toString();
            columns[1] = destinationSB.toString();
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorExecutor", "getDestinationMappingColumnDetails",
                exception.getMessage());
        }
        return columns;
    }

    /**
     * Load records.
     *
     * @param rowCount the row count
     * @param configuratorId the configurator id
     * @param mappingList the mapping list
     * @param resultSet the result set
     * @param preparedStatement the prepared statement
     * @return the int
     * @throws BODSException the bODS exception
     */
    private int loadRecords(int rowCount, int configuratorId, List<ConfiguratorColumnDefinitionVO> mappingList,
        ResultSet resultSet, PreparedStatement preparedStatement) throws BODSException {
        int count = 0;
        Object obj = null;
        String dataType = "";
        String colName = "";
        try {
            for (ConfiguratorColumnDefinitionVO columnMapping : mappingList) {
                obj = null;
                if (columnMapping != null /* && "Y".equalsIgnoreCase(columnMapping.getActiveColumnFlag() */) {
                    colName = columnMapping.getColumnName().trim();
                    dataType = columnMapping.getDataType().trim();
                    if (colName.split("\\.").length > 1) {
                        colName = colName.substring(colName.indexOf('.') + 1);
                    }
                    if (colName != null && !colName.equals("")) {
                        if (dataType != null && dataType.equalsIgnoreCase("String")) {
                            obj = resultSet.getObject(colName);
                        } else if (dataType != null && dataType.equalsIgnoreCase("Date")) {
                            obj = resultSet.getObject(colName);
                        } else {
                            obj = resultSet.getObject(colName);
                        }
                    }
                    if (obj != null) {
                        preparedStatement.setObject(++count, obj);
                    } else {
                        preparedStatement.setNull(++count, Types.NULL);
                    }
                }
            }
            System.out.println("rowCount : -" + rowCount);
            preparedStatement.setInt(++count, ++rowCount);
            preparedStatement.setInt(++count, 0);
            preparedStatement.setInt(++count, configuratorId);
            preparedStatement.setString(++count, "");
            preparedStatement.setString(++count, "");
            preparedStatement.addBatch();
            if (rowCount % 50 == 0) {
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }

        } catch (Exception exception) {
            throw new BODSException("ConfiguratorExecutor", "loadRecords", exception.getMessage());
        }
        return rowCount;
    }

    /**
     * Call staging process.
     *
     * @param configuratorId the configurator id
     * @param configuratorConnId the configurator conn id
     * @throws BODSException the bODS exception
     */
    public void callStagingProcess(int configuratorId, int configuratorConnId) throws BODSException {
        String stagingTableName = "";
        Connection connection = null;
        ConfiguratorVO configuratorVO = null;
        ConfiguratorEO configuratorEO = null;
        ConfiguratorDAO configuratorDAO = null;
        BytesUtil bytesUtil = null;
        TargetSchemaConnection targetSchemaConnection = null;
        try {
            bytesUtil = new BytesUtil();
            configuratorDAO = new ConfiguratorDAO();
            targetSchemaConnection = new TargetSchemaConnection();
            connection = targetSchemaConnection.getTargetSchemaConnection(configuratorConnId);
            configuratorEO = configuratorDAO.fetchConfigurator(configuratorId);
            configuratorVO =
                (ConfiguratorVO) bytesUtil.toObject(configuratorEO.getConfiguratorBinariesEOSet().iterator().next()
                    .getObject());
            stagingTableName = "STG_" + configuratorVO.getConfiguratorConnectionId() + "_" + configuratorId;
            callStagingProcedure(configuratorVO.getProcedureValue(), connection, stagingTableName);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorExecutor", "callStagingProcess", exception.getMessage());
        } finally {
            targetSchemaConnection.targetSchemaClose(connection, null, null, null);
        }

    }
}
