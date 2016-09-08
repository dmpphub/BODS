package com.dataprocess.bods.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.dataprocess.bods.entity.ConfiguratorEO;
import com.dataprocess.bods.entity.QueryDefinitionEO;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.util.connectionutil.HibernateSessionManager;
import com.dataprocess.bods.util.connectionutil.JDBCConnectionManager;
import com.dataprocess.bods.util.connectionutil.TargetSchemaConnection;
import com.dataprocess.bods.vo.ConcurrentParameterVO;
import com.dataprocess.bods.vo.ConfiguratorInterfaceColumnVO;
import com.dataprocess.bods.vo.ConfiguratorVO;
import com.dataprocess.bods.vo.ConfiguratorValidationVO;
import com.dataprocess.bods.vo.PrevalidationStatusVO;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfiguratorDAO.
 */
public final class ConfiguratorDAO {

    /**
     * Gets the source configurator.
     * 
     * @param sourceConfiguratorId the source configurator id
     * @return the source configurator
     * @throws BODSException the bODS exception
     */
    public QueryDefinitionEO getSourceConfigurator(int sourceConfiguratorId) throws BODSException {
        Session session = null;
        Criteria criteria = null;
        QueryDefinitionEO queryDefinitionEO = null;

        try {
            session = HibernateSessionManager.getHibernateSession();
            criteria = session.createCriteria(QueryDefinitionEO.class);
            criteria.add(Restrictions.eq("sourceConfiguratorId", sourceConfiguratorId));
            queryDefinitionEO = (QueryDefinitionEO) criteria.uniqueResult();
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getSourceConfigurator", exception.getMessage());
        } finally {

            if (session != null) {
                session.close();
            }
        }
        return queryDefinitionEO;
    }

    /**
     * Gets the interface table column.
     * 
     * @param configuratorConnectionId the configurator connection id
     * @param interfaceTableName the interface table name
     * @return the interface table column
     * @throws BODSException the bODS exception
     */
    public List<ConfiguratorInterfaceColumnVO> getInterfaceTableColumnList(int configuratorConnectionId,
        String interfaceTableName) throws BODSException {
        String query = "";
        String dataType = "";
        String columnName = "";
        ConfiguratorInterfaceColumnVO configuratorInterfaceColumnVO = null;
        List<ConfiguratorInterfaceColumnVO> configuratorInterfaceColumnVOList = null;
        TargetSchemaConnection targetSchemaConnection = null;
        Connection configuratorConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        try {
            targetSchemaConnection = new TargetSchemaConnection();
            configuratorInterfaceColumnVOList = new ArrayList<ConfiguratorInterfaceColumnVO>();
            query = "SELECT * FROM " + interfaceTableName + " WHERE 1 = 2";
            configuratorConnection = targetSchemaConnection.getTargetSchemaConnection(configuratorConnectionId);
            preparedStatement = configuratorConnection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                columnName = resultSetMetaData.getColumnName(i);
                dataType = resultSetMetaData.getColumnTypeName(i);
                if (dataType != null
                    && (dataType.equalsIgnoreCase("VARCHAR2") || dataType.equalsIgnoreCase("VARCHAR") || dataType
                        .equalsIgnoreCase("CHAR"))) {
                    dataType = "STRING";
                }
                configuratorInterfaceColumnVO = new ConfiguratorInterfaceColumnVO();
                configuratorInterfaceColumnVO.setTableName(interfaceTableName);
                configuratorInterfaceColumnVO.setColumnName(columnName);
                configuratorInterfaceColumnVOList.add(configuratorInterfaceColumnVO);
            }
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getSourceConfigurator", exception.getMessage());
        } finally {
            targetSchemaConnection.targetSchemaClose(configuratorConnection, preparedStatement, resultSet, null);
        }
        return configuratorInterfaceColumnVOList;
    }

    /**
     * Fetch.
     * 
     * @param ebsConcurrentProgramName the ebs concurrent program name
     * @param configuratorConnectionId the configurator connection id
     * @return the result set
     * @throws BODSException the bODS exception
     */
    public List<ConcurrentParameterVO> fetch(String ebsConcurrentProgramName, int configuratorConnectionId)
        throws BODSException {
        StringBuffer query = new StringBuffer();
        TargetSchemaConnection targetSchemaConnection = null;
        List<ConcurrentParameterVO> concurrentParameterVOList = null;
        Connection configuratorConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            targetSchemaConnection = new TargetSchemaConnection();
            configuratorConnection = targetSchemaConnection.getTargetSchemaConnection(configuratorConnectionId);
            query.append(" SELECT fCOL.COLUMN_SEQ_NUM, FCOL.FORM_LEFT_PROMPT, FCOL.DISPLAY_FLAG, ");
            query
                .append(" DECODE(FVS.VALIDATION_TYPE,'F','S',FCOL.DEFAULT_TYPE) AS DEFAULT_TYPE, FCOL.DEFAULT_VALUE, ");
            query.append(" FVS.VALIDATION_TYPE,DECODE(FVS.VALIDATION_TYPE, 'I', FVAL.FLEX_VALUE , 'F', 'SELECT '|| ");
            query.append(" NVL(FTAB.ID_COLUMN_NAME,FTAB.VALUE_COLUMN_NAME)||' AS ID_COLUMN_NAME ,'|| ");
            query.append(" FTAB.VALUE_COLUMN_NAME||' AS VALUE_COLUMN_NAME FROM '|| ");
            query.append(" FTAB.APPLICATION_TABLE_NAME, NULL) FLEX_VALUE, ");
            query.append(" DECODE(FVS.VALIDATION_TYPE, 'I', FVAL.DESCRIPTION, 'F','QUERY', 'N', 'NONE', NULL) ");
            query.append(" VALUE_DESC, FTAB.ADDITIONAL_WHERE_CLAUSE  WHERE_CLAUSE ");
            query.append(" FROM FND_DESCR_FLEX_COL_USAGE_VL FCOL, FND_FLEX_VALUE_SETS FVS, FND_FLEX_VALUES_VL FVAL, ");
            query.append(" FND_FLEX_VALIDATION_TABLES FTAB WHERE UPPER(FCOL.DESCRIPTIVE_FLEXFIELD_NAME) = ? AND ");
            query.append(" FCOL.ENABLED_FLAG = 'Y' AND FCOL.FLEX_VALUE_SET_ID = FVS.FLEX_VALUE_SET_ID(+) ");
            query.append(" AND FCOL.FLEX_VALUE_SET_ID = FVAL.FLEX_VALUE_SET_ID(+) ");
            query.append(" AND FCOL.FLEX_VALUE_SET_ID = FTAB.FLEX_VALUE_SET_ID(+) ");
            query.append(" ORDER BY FCOL.COLUMN_SEQ_NUM ");
            preparedStatement = configuratorConnection.prepareStatement(query.toString());
            preparedStatement.setString(1, "$SRS$." + ebsConcurrentProgramName.toUpperCase());
            resultSet = preparedStatement.executeQuery();
            concurrentParameterVOList = fillConcurrentProgramList(resultSet);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "fetch", exception.getMessage());
        }

        return concurrentParameterVOList;
    }

    /**
     * Fill concurrent program list.
     *
     * @param resultSet the result set
     * @return the list
     * @throws BODSException the bODS exception
     */
    private List<ConcurrentParameterVO> fillConcurrentProgramList(ResultSet resultSet) throws BODSException {
        ConcurrentParameterVO concurrentParameterVO = null;
        List<ConcurrentParameterVO> concurrentParameterVOList = null;
        try {
            concurrentParameterVOList = new ArrayList<ConcurrentParameterVO>();
            if (resultSet.wasNull()) {
                while (resultSet.next()) {
                    concurrentParameterVO = new ConcurrentParameterVO();
                    concurrentParameterVO.setParameterIndex(resultSet.getInt(1));
                    concurrentParameterVO.setName(resultSet.getString(2));
                    concurrentParameterVO.setParameterType("IN");
                    concurrentParameterVO.setInputType("Text");
                    concurrentParameterVO.setFlexValue(resultSet.getString("FLEX_VALUE"));
                    concurrentParameterVO.setValueDesc(resultSet.getString("VALUE_DESC"));
                    concurrentParameterVO.setWhereClause(resultSet.getString("WHERE_CLAUSE"));
                    concurrentParameterVOList.add(concurrentParameterVO);
                }
            }
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "fillConcurrentProgramList", exception.getMessage());
        }
        return concurrentParameterVOList;
    }

    /*
     * save and update fetch
     */

    /**
     * Gets the config connection list.
     *
     * @return the config connection list
     * @throws BODSException the bODS exception
     */
    public ArrayList<ConfiguratorVO> getConfigConnectionList() throws BODSException {
        StringBuffer queryBuffer = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConfiguratorVO configuratorVO = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        ArrayList<ConfiguratorVO> configConnectionList = null;
        try {
            jdbcConnectionManager = new JDBCConnectionManager();
            queryBuffer = new StringBuffer();
            configConnectionList = new ArrayList<ConfiguratorVO>();
            if (jdbcConnectionManager.getJDBCConnection()) {
                queryBuffer.append(" SELECT ");
                queryBuffer.append(" CONNECTION_NAME, CONNECTION_ID ");
                queryBuffer.append(" FROM ");
                queryBuffer.append(" BODS_CONNECTION_CFG ");
                connection = jdbcConnectionManager.getConnection();
                ps = connection.prepareStatement(queryBuffer.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    configuratorVO = new ConfiguratorVO();
                    configuratorVO.setConfiguratorConnectionName(rs.getString("CONNECTION_NAME"));
                    configuratorVO.setConfiguratorConnectionId(rs.getInt("CONNECTION_ID"));
                    configConnectionList.add(configuratorVO);
                }
            }
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getConfigConnectionList", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
        return configConnectionList;
    }

    /**
     * Gets the source config connection list.
     *
     * @return the source config connection list
     * @throws BODSException the bODS exception
     */
    public ArrayList<ConfiguratorVO> getSourceConfigConnectionList() throws BODSException {
        StringBuffer queryBuffer = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConfiguratorVO configuratorVO = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        ArrayList<ConfiguratorVO> sourceConfigConnctionList = null;
        try {
            jdbcConnectionManager = new JDBCConnectionManager();
            queryBuffer = new StringBuffer();
            sourceConfigConnctionList = new ArrayList<ConfiguratorVO>();
            if (jdbcConnectionManager.getJDBCConnection()) {
                queryBuffer.append(" SELECT ");
                queryBuffer.append(" CONNECTION_NAME, CONNECTION_ID ");
                queryBuffer.append(" FROM ");
                queryBuffer.append(" BODS_CONNECTION_CFG ");
                connection = jdbcConnectionManager.getConnection();
                ps = connection.prepareStatement(queryBuffer.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    configuratorVO = new ConfiguratorVO();
                    configuratorVO.setSourceConfigurationConnectionName(rs.getString("CONNECTION_NAME"));
                    configuratorVO.setSourceConfigurationConnectionId(rs.getInt("CONNECTION_ID"));
                    sourceConfigConnctionList.add(configuratorVO);
                }
            }
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getSourceConfigConnectionList", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
        return sourceConfigConnctionList;
    }

    /**
     * Gets the source configuration name list.
     *
     * @return the source configuration name list
     * @throws BODSException the bODS exception
     */
    public ArrayList<ConfiguratorVO> getSourceConfigurationNameList() throws BODSException {
        StringBuffer queryBuffer = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConfiguratorVO configuratorVO = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        ArrayList<ConfiguratorVO> sourceConfigurationNameList = null;
        try {
            jdbcConnectionManager = new JDBCConnectionManager();
            queryBuffer = new StringBuffer();
            sourceConfigurationNameList = new ArrayList<ConfiguratorVO>();
            if (jdbcConnectionManager.getJDBCConnection()) {
                queryBuffer.append(" SELECT ");
                queryBuffer.append(" SOURCE_CFG_NAME, SOURCE_CFG_ID ");
                queryBuffer.append(" FROM ");
                queryBuffer.append(" BODS_SOURCE_CFG ");
                connection = jdbcConnectionManager.getConnection();
                ps = connection.prepareStatement(queryBuffer.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    configuratorVO = new ConfiguratorVO();
                    configuratorVO.setSourceConfigurationName(rs.getString("SOURCE_CFG_NAME"));
                    configuratorVO.setSourceConfigurationId(rs.getInt("SOURCE_CFG_ID"));
                    sourceConfigurationNameList.add(configuratorVO);
                }
            }
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getSourceConfigurationNameList", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
        return sourceConfigurationNameList;
    }

    /**
     * Save configurator details.
     *
     * @param configuratorEO the configurator eo
     * @return the configurator eo
     * @throws BODSException the bODS exception
     */
    public ConfiguratorEO saveConfiguratorDetails(ConfiguratorEO configuratorEO) throws BODSException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateSessionManager.getHibernateSession();
            transaction = session.beginTransaction();
            configuratorEO = (ConfiguratorEO) session.merge(configuratorEO);
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
            throw new BODSException("ConfiguratorDAO", "getSourceConfigurationNameList", exception.getMessage());
        } finally {
            session.close();
        }
        return configuratorEO;
    }

    /**
     * Creates the staging table.
     *
     * @param connection the connection
     * @param tableScripts the table scripts
     * @param stagingTableName the staging table name
     * @throws BODSException the bODS exception
     */
    public void createStagingTable(Connection connection, String tableScripts, String stagingTableName)
        throws BODSException {
        TargetSchemaConnection targetSchemaConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            targetSchemaConnection = new TargetSchemaConnection();
            dropTableIfExists(stagingTableName, connection, targetSchemaConnection);
            preparedStatement = connection.prepareStatement(tableScripts);
            resultSet = preparedStatement.executeQuery();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "createStagingTable", exception.getMessage());
        } finally {
            targetSchemaConnection.targetSchemaClose(null, preparedStatement, resultSet, null);
        }
    }

    /**
     * Creates the prevalidation table.
     *
     * @param connection the connection
     * @param tableScripts the table scripts
     * @param pvTableName the pv table name
     * @throws BODSException the bODS exception
     */
    public void createPrevalidationTable(Connection connection, String tableScripts, String pvTableName)
        throws BODSException {
        TargetSchemaConnection targetSchemaConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            targetSchemaConnection = new TargetSchemaConnection();
            dropTableIfExists(pvTableName, connection, targetSchemaConnection);
            preparedStatement = connection.prepareStatement(tableScripts);
            resultSet = preparedStatement.executeQuery();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "createPrevalidationTable", exception.getMessage());
        } finally {
            targetSchemaConnection.targetSchemaClose(null, preparedStatement, resultSet, null);
        }
    }

    /**
     * Insert prevalidation values.
     *
     * @param connection the connection
     * @param pvTableName the pv table name
     * @param configuratorVO the configurator vo
     * @return the string
     * @throws BODSException the bODS exception
     */
    public String insertPrevalidationValues(Connection connection, String pvTableName, ConfiguratorVO configuratorVO)
        throws BODSException {
        int index = 0;
        int count = 1;
        String pvColumn = "";
        StringBuffer insertQueryFormation = null;
        PreparedStatement preparedStatement = null;
        TargetSchemaConnection targetSchemaConnection = null;
        List<ConfiguratorValidationVO> configuratorValidationVOList;
        try {
            insertQueryFormation = new StringBuffer();
            targetSchemaConnection = new TargetSchemaConnection();
            configuratorValidationVOList = configuratorVO.getConfiguratorValidationVOList();
            configuratorValidationVOList.add(configuratorVO.getConfiguratorValidationVO());
            insertQueryFormation.append(" INSERT INTO " + pvTableName + "(PV_SEQ_ID, EXECUTION_ID, ");
            insertQueryFormation.append(" CONFIGURATOR_ID, PV_SEQ_ORDER_NO, PV_TYPE, PV_ATTRIBUTE,");
            insertQueryFormation.append(" SUCCESS_MESSAGE, ERROR_MESSAGE, SUCCESS_COUNT, ERROR_COUNT,");
            insertQueryFormation.append(" START_TIME,END_TIME) VALUES ");
            insertQueryFormation.append(" (?,?,?,?,?,?,?,?,?,?,?,?) ");
            preparedStatement = connection.prepareStatement(insertQueryFormation.toString());
            for (ConfiguratorValidationVO configuratorValidationVO : configuratorValidationVOList) {
                index = 0;
                preparedStatement.setInt(++index, count);
                preparedStatement.setInt(++index, 0);
                preparedStatement.setInt(++index, configuratorVO.getConfiguratorId());
                preparedStatement.setString(++index, "PV_" + count);
                pvColumn = ", PV_" + count;
                preparedStatement.setString(++index, "M");
                preparedStatement.setString(++index, configuratorValidationVO.getName());
                preparedStatement.setString(++index, configuratorValidationVO.getDisplayName());
                preparedStatement.setString(++index, configuratorValidationVO.getValidationErrorMessage());
                preparedStatement.setInt(++index, 0);
                preparedStatement.setInt(++index, 0);
                preparedStatement.setString(++index, "");
                preparedStatement.setString(++index, "");
                preparedStatement.addBatch();
                count++;
                if (count % 50 == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            if (count % 50 != 0) {
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "insertPrevalidationValues", exception.getMessage());
        } finally {
            targetSchemaConnection.targetSchemaClose(null, preparedStatement, null, null);
        }
        return pvColumn;
    }

    /**
     * Drop table if exists.
     * 
     * @param stagingTableName the staging table name
     * @param connection the connection
     * @param targetSchemaConnection the target schema connection
     * @throws BODSException the bODS exception
     */
    private void dropTableIfExists(String stagingTableName, Connection connection,
        TargetSchemaConnection targetSchemaConnection) throws BODSException {
        String query = "";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            query = " DROP TABLE " + stagingTableName;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            targetSchemaConnection.targetSchemaClose(null, preparedStatement, resultSet, null);
        }
    }

    /**
     * Delete staging table records.
     *
     * @param stagingTableName the staging table name
     * @param connection the connection
     * @throws BODSException the bODS exception
     */
    public void deleteStagingTableRecords(String stagingTableName, Connection connection) throws BODSException {
        String deleteTableScript = "";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        TargetSchemaConnection targetSchemaConnection = null;
        try {
            targetSchemaConnection = new TargetSchemaConnection();
            deleteTableScript = " DELETE FROM " + stagingTableName;
            preparedStatement = connection.prepareStatement(deleteTableScript);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "deleteStagingTableRecords", exception.getMessage());
        } finally {
            targetSchemaConnection.targetSchemaClose(null, preparedStatement, resultSet, null);
        }
    }

    /**
     * Update prevalidation count for next execution.
     *
     * @param prevalidationTableName the prevalidation table name
     * @param connection the connection
     * @throws BODSException the bODS exception
     */
    public void updatePrevalidationCountForNextExecution(String prevalidationTableName, Connection connection)
        throws BODSException {
        String updateTableScript = "";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        TargetSchemaConnection targetSchemaConnection = null;
        try {
            targetSchemaConnection = new TargetSchemaConnection();
            updateTableScript = " UPDATE " + prevalidationTableName + " SET SUCCESS_COUNT=0, ERROR_COUNT=0";
            preparedStatement = connection.prepareStatement(updateTableScript);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "updatePrevalidationCountForNextExecution",
                exception.getMessage());
        } finally {
            targetSchemaConnection.targetSchemaClose(null, preparedStatement, resultSet, null);
        }
    }

    /**
     * Fetch transformation api.
     * 
     * @param configuratorId the configurator id
     * @return the configurator eo
     * @throws BODSException the bODS exception
     */
    public ConfiguratorEO fetchConfigurator(int configuratorId) throws BODSException {
        Session session = null;
        Criteria criteria = null;
        ConfiguratorEO configuratorEO = null;
        try {
            session = HibernateSessionManager.getHibernateSession();
            criteria = session.createCriteria(ConfiguratorEO.class);
            criteria.add(Restrictions.eq("configuratorId", configuratorId));
            configuratorEO = (ConfiguratorEO) criteria.uniqueResult();
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "createStagingTable", exception.getMessage());
        } finally {
            if (session != null) {
                session.clear();
                session.close();
            }
        }
        return configuratorEO;
    }

    /**
     * Gets the extract query from db.
     *
     * @param sourceConfiguratorId the source configurator id
     * @return the extract query from db
     * @throws BODSException the bODS exception
     */
    public String getExtractQueryFromDB(int sourceConfiguratorId) throws BODSException {
        String sourceQuery = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        try {
            jdbcConnectionManager = new JDBCConnectionManager();
            sourceQuery = "SELECT SQL_QUERY FROM BODS_SOURCE_CFG WHERE SOURCE_CFG_ID =" + sourceConfiguratorId;
            if (jdbcConnectionManager.getJDBCConnection()) {
                connection = jdbcConnectionManager.getConnection();
                preparedStatement = connection.prepareStatement(sourceQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    sourceQuery = resultSet.getString("SQL_QUERY");
                }
            }
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getExtractQueryFromDB", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, preparedStatement, resultSet);
        }
        return sourceQuery;
    }

    /**
     * Update status code block.
     *
     * @param connection the connection
     * @param stagingTableName the staging table name
     * @throws BODSException the bODS exception
     */
    public boolean updateStatusCodeBlock(Connection connection, String stagingTableName, ConfiguratorVO configuratorVO)
        throws BODSException {
        int index = 0;
        int updateCount = 0;
        boolean updateFlag = false;
        StringBuffer dcStatus = null;
        PreparedStatement preparedStatement = null;
        List<String> prevalidationSeqNo = null;
        try {
            prevalidationSeqNo = configuratorVO.getPrevalidationSeqNo();
            dcStatus = new StringBuffer();
            dcStatus.append(" UPDATE " + stagingTableName + " SET STATUS_CODE = ");
            dcStatus.append("  CASE WHEN ( ");
            for (String prevalSeq : prevalidationSeqNo) {
                if (index == 0) {
                    dcStatus.append(" (" + prevalSeq + " != 'S' ) ");
                    index++;
                } else {
                    dcStatus.append(" OR (" + prevalSeq + " != 'S' ) ");
                }
            }
            dcStatus.append(" ) THEN 'E' ELSE 'S' END; ");
            preparedStatement = connection.prepareStatement(dcStatus.toString());
            updateCount = preparedStatement.executeUpdate();
            if (updateCount > 0) {
                updateFlag = true;
            }
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getExtractQueryFromDB", exception.getMessage());
        }
        return updateFlag;

    }

    /**
     * Builds the staging dc flag status.
     *
     * @param connection the connection
     * @param loaderExecutionId the loader execution id
     * @param stagingTableName the staging table name
     * @throws BODSException the bODS exception
     */
    public void buildStagingDCFlagStatus(Connection connection, int loaderExecutionId, String stagingTableName)
        throws BODSException {
        StringBuffer dcStatus = null;
        String procedure = "";
        PreparedStatement preparedStatement = null;
        try {
            dcStatus = new StringBuffer();
            dcStatus.append(" BEGIN \n");
            dcStatus.append(" UPDATE " + stagingTableName + " SET DC_FLAG ='S' \n");
            dcStatus.append(" WHERE STATUS_CODE = 'S' AND EXECUTION_ID = ##EXECUTION_ID##; \n");
            dcStatus.append(" UPDATE " + stagingTableName + " SET DC_FLAG ='E' \n");
            dcStatus.append(" WHERE STATUS_CODE = 'E' AND EXECUTION_ID = ##EXECUTION_ID##; \n");
            dcStatus.append(" COMMIT; \n");
            dcStatus.append(" END; \n");
            procedure = dcStatus.toString().replaceAll("##EXECUTION_ID##", String.valueOf(loaderExecutionId));
            System.out.println("After DC Flag Replace Value :\n" + procedure);
            preparedStatement = connection.prepareCall(procedure);
            preparedStatement.executeQuery();
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getExtractQueryFromDB", exception.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException sqlException) {
                    throw new BODSException("ConfiguratorDAO", "getExtractQueryFromDB", sqlException.getMessage());
                }
            }
        }
    }

    /**
     * Fetch configurator list.
     *
     * @param configuratorVO the configurator vo
     * @throws BODSException the bODS exception
     */
    public void fetchConfiguratorList(ConfiguratorVO configuratorVO) throws BODSException {
        StringBuffer queryBuffer = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        ConfiguratorVO listVO = null;
        try {
            jdbcConnectionManager = new JDBCConnectionManager();
            queryBuffer = new StringBuffer();
            configuratorVO.setConfiguratorVOList(new ArrayList<ConfiguratorVO>());

            if (jdbcConnectionManager.getJDBCConnection()) {
                queryBuffer.append(" SELECT ");
                queryBuffer
                    .append(" BODS_CON.CONNECTION_NAME, BODS_CON.CONNECTION_ID, BODS_CFG.CONFIGURATOR_NAME, BODS_CFG.CONFIGURATOR_ID ");
                queryBuffer.append(" FROM ");
                queryBuffer.append(" BODS_CONNECTION_CFG BODS_CON, BODS_CONFIGURATOR_CFG BODS_CFG ");
                queryBuffer.append(" WHERE ");
                queryBuffer.append(" BODS_CON.CONNECTION_ID = BODS_CFG.CONFIGURATOR_CONN_ID ");

                connection = jdbcConnectionManager.getConnection();
                ps = connection.prepareStatement(queryBuffer.toString());
                rs = ps.executeQuery();

                while (rs.next()) {
                    listVO = new ConfiguratorVO();
                    listVO.setConfiguratorConnectionName(rs.getString("CONNECTION_NAME"));
                    listVO.setConfiguratorConnectionId(rs.getInt("CONNECTION_ID"));
                    listVO.setConfiguratorName(rs.getString("CONFIGURATOR_NAME"));
                    listVO.setConfiguratorId(rs.getInt("CONFIGURATOR_ID"));
                    configuratorVO.getConfiguratorVOList().add(listVO);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BODSException("ConfiguratorDAO", "fetchQueryDefinitionList", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
    }

    /**
     * Gets the stagint table detail for pie chart.
     *
     * @param stagingTableName the staging table name
     * @return the stagint table detail for pie chart
     * @throws BODSException the bODS exception
     */
    public String getStagingTableDetailForPieChart(String stagingTableName) throws BODSException {
        String chartValueStr = "";
        StringBuffer query = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        try {
            jdbcConnectionManager = new JDBCConnectionManager();
            if (jdbcConnectionManager.getJDBCConnection()) {
                query = new StringBuffer();
                query.append(" SELECT COUNT (1) TOTAL_REC,  ");
                query.append(" SUM (CASE   ");
                query.append(" WHEN STG.STATUS_CODE ='S' THEN 1 ");
                query.append(" WHEN STG.STATUS_CODE ='V' THEN 1 ");
                query.append(" ELSE 0 END) AS SUCCESS_REC, ");
                query.append(" SUM (CASE  ");
                query.append(" WHEN STG.STATUS_CODE ='E' THEN 1 ");
                query.append(" ELSE 0 END) ERROR_REC ");
                query.append(" FROM  " + stagingTableName + " STG ");
                connection = jdbcConnectionManager.getConnection();
                ps = connection.prepareStatement(query.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    chartValueStr = rs.getString("TOTAL_REC");
                    chartValueStr += "##" + rs.getString("SUCCESS_REC");
                    chartValueStr += "##" + rs.getString("ERROR_REC");
                    System.out.println("chartValueStr :\n" + chartValueStr);
                }
            }
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getStagintTableDetailForPieChart", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
        return chartValueStr;
    }

    /**
     * Gets the prevalidation status.
     *
     * @param stagingTableName the staging table name
     * @return the prevalidation status
     * @throws BODSException the bODS exception
     */
    public List<PrevalidationStatusVO> getPrevalidationStatus(String stagingTableName) throws BODSException {
        StringBuffer query = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PrevalidationStatusVO prevalidationStatusVO = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        List<PrevalidationStatusVO> prevalidationStatusVOList = null;
        try {
            query = new StringBuffer();
            prevalidationStatusVOList = new ArrayList<PrevalidationStatusVO>();
            jdbcConnectionManager = new JDBCConnectionManager();
            if (jdbcConnectionManager.getJDBCConnection()) {
                query.append("SELECT SUCCESS_MESSAGE,ERROR_MESSAGE,SUCCESS_COUNT,ERROR_COUNT,PV_ATTRIBUTE FROM "
                    + stagingTableName + " ORDER BY PV_SEQ_ID");
                connection = jdbcConnectionManager.getConnection();
                ps = connection.prepareStatement(query.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    prevalidationStatusVO = new PrevalidationStatusVO();
                    prevalidationStatusVO.setSuccessMsg(rs.getString("SUCCESS_MESSAGE").toString());
                    if (rs.getString("ERROR_MESSAGE") != null) {
                        prevalidationStatusVO.setErrorMsg(rs.getString("ERROR_MESSAGE").toString());
                    }
                    prevalidationStatusVO.setSuccessCount(String.valueOf(rs.getInt("SUCCESS_COUNT")));
                    prevalidationStatusVO.setErrorCount(String.valueOf(rs.getInt("ERROR_COUNT")));
                    prevalidationStatusVO.setPv_attribute(rs.getString("PV_ATTRIBUTE"));
                    prevalidationStatusVOList.add(prevalidationStatusVO);
                }
            }
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getPrevalidationStatus", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
        return prevalidationStatusVOList;
    }
}
