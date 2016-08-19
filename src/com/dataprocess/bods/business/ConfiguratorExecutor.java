package com.dataprocess.bods.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.dataprocess.bods.dao.ConfiguratorDAO;
import com.dataprocess.bods.entity.ConfiguratorEO;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.util.SpringBeanUtils;
import com.dataprocess.bods.util.connectionutil.TargetSchemaConnection;
import com.dataprocess.bods.vo.ConfiguratorColumnDefinitionVO;
import com.dataprocess.bods.vo.ConfiguratorVO;

/**
 * The Class ConfiguratorExecutor.
 */
public final class ConfiguratorExecutor {

	/**
	 * Loader staging table insert.
	 * 
	 * @throws BODSException
	 *             the bODS exception
	 */
	public void loaderStagingTableInsert(int configuratorId,int configuratorConnId) throws BODSException {
		String stagingTableName = "";
		ConfiguratorVO configuratorVO = null;
		ConfiguratorEO configuratorEO = null;
		ConfiguratorDAO configuratorDAO = null;
		Connection connection = null;
		Connection sourceConnection = null;
		TargetSchemaConnection targetSchemaConnection = null;
		try {
			configuratorDAO = new ConfiguratorDAO();
			configuratorVO = new ConfiguratorVO();
			targetSchemaConnection = new TargetSchemaConnection();
			connection = targetSchemaConnection.getTargetSchemaConnection(configuratorConnId);
			configuratorEO = configuratorDAO.fetchConfigurator(configuratorId);
			configuratorVO = (ConfiguratorVO) new SpringBeanUtils().populateToDTOObject(configuratorEO, configuratorVO);
			sourceConnection = targetSchemaConnection
					.getTargetSchemaConnection(configuratorVO.getSourceConfigurationConnectionId());
			stagingTableName = "STG_" + configuratorVO.getConfiguratorConnectionId() + "_"
					+ configuratorVO.getConfiguratorId();
			loadRecordsIntoStagingTable(stagingTableName, configuratorVO,connection,sourceConnection);
			callStagingProcedure(configuratorVO.getProcedureValue(),connection,stagingTableName);
		} catch(BODSException bodsException) {
			throw bodsException;
		}catch (Exception exception) {
			throw new BODSException("ConfiguratorExecutor", "loaderStagingTableInsert", exception.getMessage());
		} finally {
			targetSchemaConnection.targetSchemaClose(connection, null, null, null);
			targetSchemaConnection.targetSchemaClose(sourceConnection, null, null, null);
		}
	}

	private void callStagingProcedure(String procedure,Connection connection,String stagingTableName) throws BODSException{
		PreparedStatement preparedStatement = null;
		try {
			if(procedure.indexOf("##stagingTableName##") != -1) {
				procedure = procedure.replaceAll("##stagingTableName##", stagingTableName);
			}
			preparedStatement = connection.prepareCall(procedure);
			preparedStatement.executeQuery();
		} catch (Exception exception) {
			throw new BODSException("ConfiguratorExecutor", "callStagingProcedure", exception.getMessage());
		} finally {
			if(preparedStatement != null) {
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
	 * @param stagingTableName
	 *            the staging table name
	 * @throws BODSException
	 *             the bODS exception
	 */
	private void loadRecordsIntoStagingTable(String stagingTableName, ConfiguratorVO configuratorVO,Connection connection,Connection sourceConnection)
			throws BODSException {
		int rowCount = 0;
		StringBuffer sourceQuery = new StringBuffer();
		String[] destColumnNames = null;
		TargetSchemaConnection targetSchemaConnection = null;
		List<ConfiguratorColumnDefinitionVO> mappingList = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			targetSchemaConnection = new TargetSchemaConnection();
			resultSet = getExtractResultSet(configuratorVO, sourceConnection);
			mappingList = new ArrayList<>(configuratorVO.getConfiguratorColumnDefinitionVOSet());
			destColumnNames = getDestinationMappingColumnDetails(mappingList);
			sourceQuery.append(" INSERT INTO  ");
			sourceQuery.append(stagingTableName);
			sourceQuery.append(" (" + destColumnNames[1] + ") ");
			sourceQuery.append(" VALUES (" + destColumnNames[0] + ") ");
			preparedStatement = connection.prepareStatement(sourceQuery.toString());
			while (resultSet.next()) {
				rowCount = loadRecords(rowCount, mappingList, resultSet, preparedStatement);
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

	}

	private ResultSet getExtractResultSet(ConfiguratorVO configuratorVO, Connection sourceConnection) {
		String sourceQuery = "";
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		ConfiguratorDAO configuratorDAO = null;
		try {
			configuratorDAO = new ConfiguratorDAO();
			sourceQuery = configuratorDAO.getExtractQueryFromDB(configuratorVO.getSourceConfigurationId());
			preparedStatement = sourceConnection.prepareStatement(sourceQuery);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			/*if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}*/
		}
		return resultSet;
	}

	/**
	 * Gets the destination mapping column details.
	 * 
	 * @param mappingList
	 *            the mapping list
	 * @return the destination mapping column details
	 * @throws BODSException
	 *             the bODS exception
	 */
	private String[] getDestinationMappingColumnDetails(List<ConfiguratorColumnDefinitionVO> mappingList)
			throws BODSException {
		StringBuffer destinationSB = new StringBuffer();
		StringBuffer sourceSB = new StringBuffer();
		int count = 0;
		String columns[] = new String[2];
		try {
			for (ConfiguratorColumnDefinitionVO columnMapping : mappingList) {
				/*if ("Y".equals(columnMapping.getActiveColumnFlag())) {*/
					if (count != 0) {
						destinationSB.append(",");
						sourceSB.append(",");
					}
					count++;
				//}
				destinationSB.append(columnMapping.getColumnName());
				sourceSB.append("?");
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
	 * @param mappingList
	 *            the mapping list
	 * @return the int
	 * @throws BODSException
	 *             the bODS exception
	 */
	private int loadRecords(int rowCount, List<ConfiguratorColumnDefinitionVO> mappingList, ResultSet resultSet,
			PreparedStatement preparedStatement) throws BODSException {
		int count = 0;
		Object obj = null;
		String dataType = "";
		String colName = "";
		try {
			for (ConfiguratorColumnDefinitionVO columnMapping : mappingList) {
				obj = null;
				/*if ("Y".equals(columnMapping.getActiveColumnFlag())) {*/
					dataType = columnMapping.getDataType();
					colName = columnMapping.getColumnName();
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
				//}
			}
			/*
			 * preparedStatement.setString(++count, "");
			 * preparedStatement.setString(++count, "");
			 * preparedStatement.setLong(++count,
			 * migMapping.getDataTemplateId());
			 * preparedStatement.setLong(++count, userId);
			 * preparedStatement.setLong(++count, ldrExecId);
			 * preparedStatement.setInt(++count, ++rowCount);
			 * preparedStatement.setString(++count, statusMessage);
			 */
			preparedStatement.addBatch();
			if (rowCount % 50 == 0) {
				preparedStatement.executeBatch();
				preparedStatement.clearBatch();
			}

		} catch (Exception exception) {
			throw new BODSException("ConfiguratorExecutor", "loadRecords", exception.getMessage());
		}
		return count;

	}
}
