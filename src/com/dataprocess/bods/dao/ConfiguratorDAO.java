package com.dataprocess.bods.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
            /*if (resultSet.next()) {*/
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
            //}

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
     * @param targetConnectionId the target connection id
     * @param tableScripts the table scripts
     * @param stagingTableName the staging table name
     * @throws BODSException the bODS exception
     */
    public void createStagingTable(int targetConnectionId, String tableScripts, String stagingTableName)
        throws BODSException {
        TargetSchemaConnection targetSchemaConnection = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            targetSchemaConnection = new TargetSchemaConnection();
            connection = targetSchemaConnection.getTargetSchemaConnection(targetConnectionId);
            dropTableIfExists(stagingTableName, connection, targetSchemaConnection);
            preparedStatement = connection.prepareStatement(tableScripts);
            resultSet = preparedStatement.executeQuery();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "createStagingTable", exception.getMessage());
        } finally {
            targetSchemaConnection.targetSchemaClose(connection, preparedStatement, resultSet, null);
        }
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
}
