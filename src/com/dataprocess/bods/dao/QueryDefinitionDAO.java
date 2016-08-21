package com.dataprocess.bods.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashSet;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.dataprocess.bods.entity.QueryDefinitionEO;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.util.connectionutil.HibernateSessionManager;
import com.dataprocess.bods.util.connectionutil.JDBCConnectionManager;
import com.dataprocess.bods.vo.QueryDefinitionLineVO;
import com.dataprocess.bods.vo.QueryDefinitionVO;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryDefinitionDAO.
 */
public final class QueryDefinitionDAO {

    /**
     * Validate query definition.
     *
     * @param queryDefinitionVO the query definition vo
     * @return true, if successful
     */
    public boolean validateQueryDefinition(QueryDefinitionVO queryDefinitionVO) throws BODSException {
        boolean hasValidated = false;
        String query = "";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData metaData = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        QueryDefinitionLineVO queryDefinitionLineVO = null;
        HashSet<QueryDefinitionLineVO> queryDefinitionLineVOSet = null;
        try {
            jdbcConnectionManager = new JDBCConnectionManager();
            if (jdbcConnectionManager.getJDBCConnection()) {
                connection = jdbcConnectionManager.getConnection();
                query = queryDefinitionVO.getSqlQuery().replaceAll(";", "") + " WHERE 1 = 2 ";
                ps = connection.prepareStatement(query);
                rs = ps.executeQuery();
                metaData = rs.getMetaData();
                if (metaData != null) {
                    queryDefinitionVO.setQueryDefinitionLineVOList(new ArrayList<QueryDefinitionLineVO>());
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        queryDefinitionLineVO = new QueryDefinitionLineVO();
                        queryDefinitionLineVO.setColumnName(metaData.getColumnName(i));
                        if (metaData.getColumnTypeName(i).equals("VARCHAR")
                            || metaData.getColumnTypeName(i).equals("VARCHAR2")
                            || metaData.getColumnTypeName(i).equals("CHAR")) {
                            queryDefinitionLineVO.setDataType("STRING");
                        } else {
                            queryDefinitionLineVO.setDataType(metaData.getColumnTypeName(i));
                        }
                        queryDefinitionVO.getQueryDefinitionLineVOList().add(queryDefinitionLineVO);
                    }
                    hasValidated = true;
                }
                if (queryDefinitionVO.getQueryDefinitionLineVOList() != null
                    && queryDefinitionVO.getQueryDefinitionLineVOList().size() > 0) {
                    queryDefinitionLineVOSet =
                        new HashSet<QueryDefinitionLineVO>(queryDefinitionVO.getQueryDefinitionLineVOList());
                    queryDefinitionVO.setSourceConfiguratorLineVOSet(queryDefinitionLineVOSet);
                }
            }
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionHandler", "validateQueryDefinition", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
        return hasValidated;
    }

    /**
     * Save query definition.
     *
     * @param queryDefinitionEO the query definition eo
     * @return the query definition eo
     */
    public QueryDefinitionEO saveQueryDefinition(QueryDefinitionEO queryDefinitionEO) throws BODSException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateSessionManager.getHibernateSession();
            transaction = session.beginTransaction();
            queryDefinitionEO = (QueryDefinitionEO) session.merge(queryDefinitionEO);
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
            throw new BODSException("QueryDefinitionDAO", "saveQueryDefinition", exception.getMessage());
        } finally {
            session.close();
        }
        return queryDefinitionEO;
    }

    /**
     * Gets the source config connection list.
     *
     * @return the source config connection list
     * @throws BODSException the bODS exception
     */
    public ArrayList<QueryDefinitionVO> getSourceConfigConnectionList() throws BODSException {
        StringBuffer queryBuffer = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        QueryDefinitionVO definitionVO = null;
        ArrayList<QueryDefinitionVO> sourceConfigConnctionList = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        try {
            jdbcConnectionManager = new JDBCConnectionManager();
            queryBuffer = new StringBuffer();
            sourceConfigConnctionList = new ArrayList<QueryDefinitionVO>();
            if (jdbcConnectionManager.getJDBCConnection()) {
                queryBuffer.append(" SELECT CONNECTION_NAME, CONNECTION_ID ");
                queryBuffer.append(" FROM BODS_CONNECTION_CFG");
                connection = jdbcConnectionManager.getConnection();
                ps = connection.prepareStatement(queryBuffer.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    definitionVO = new QueryDefinitionVO();
                    definitionVO.setSourceConfigConnection(rs.getString("CONNECTION_NAME"));
                    definitionVO.setSourceConfigConnectionId(rs.getInt("CONNECTION_ID"));
                    sourceConfigConnctionList.add(definitionVO);
                }
            }
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionDAO", "getSourceConfigConnectionList", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
        return sourceConfigConnctionList;
    }

    /**
     * Fetch query definition list.
     *
     * @param queryDefinitionVO the query definition vo
     */
    public void fetchQueryDefinitionList(QueryDefinitionVO queryDefinitionVO) throws BODSException {
        StringBuffer queryBuffer = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        QueryDefinitionVO definitionVO = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        try {
            jdbcConnectionManager = new JDBCConnectionManager();
            queryBuffer = new StringBuffer();
            queryDefinitionVO.setQueryDefinitionVOList(new ArrayList<QueryDefinitionVO>());
            if (jdbcConnectionManager.getJDBCConnection()) {
                queryBuffer.append(" SELECT BODS_CON.CONNECTION_NAME, BODS_SOURCE.SOURCE_CFG_NAME, ");
                queryBuffer.append(" BODS_SOURCE.SOURCE_CFG_ID FROM ");
                queryBuffer.append(" BODS_CONNECTION_CFG BODS_CON, BODS_SOURCE_CFG BODS_SOURCE WHERE ");
                queryBuffer.append(" BODS_CON.CONNECTION_ID = BODS_SOURCE.CONNECTION_ID ");
                connection = jdbcConnectionManager.getConnection();
                ps = connection.prepareStatement(queryBuffer.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    definitionVO = new QueryDefinitionVO();
                    definitionVO.setSourceConfigConnection(rs.getString("CONNECTION_NAME"));
                    definitionVO.setSourceConfigNameId(rs.getInt("SOURCE_CFG_ID"));
                    definitionVO.setSourceConfigName(rs.getString("SOURCE_CFG_NAME"));
                    queryDefinitionVO.getQueryDefinitionVOList().add(definitionVO);
                }
            }
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionDAO", "fetchQueryDefinitionList", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
    }

    /**
     * Fetch query definition details.
     *
     * @param queryDefinitionVO the query definition vo
     * @return the query definition eo
     * @throws BODSException the bODS exception
     */
    public QueryDefinitionEO fetchQueryDefinitionDetails(QueryDefinitionVO queryDefinitionVO) throws BODSException {
        Session session = null;
        Criteria criteria = null;
        QueryDefinitionEO queryDefinitionEO = null;
        try {
            session = HibernateSessionManager.getHibernateSession();
            criteria = session.createCriteria(QueryDefinitionEO.class);
            criteria.add(Restrictions.eq("sourceConfiguratorId", queryDefinitionVO.getSourceConfigNameId()));
            queryDefinitionEO = (QueryDefinitionEO) criteria.uniqueResult();
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "createStagingTable", exception.getMessage());
        } finally {
            if (session != null) {
                session.clear();
                session.close();
            }
        }
        return queryDefinitionEO;
    }
    
}
