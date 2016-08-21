package com.dataprocess.bods.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dataprocess.bods.dao.QueryDefinitionDAO;
import com.dataprocess.bods.entity.QueryDefinitionEO;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.util.BytesUtil;
import com.dataprocess.bods.util.SpringBeanUtils;
import com.dataprocess.bods.util.connectionutil.JDBCConnectionManager;
import com.dataprocess.bods.vo.QueryDefinitionDataVO;
import com.dataprocess.bods.vo.QueryDefinitionLineVO;
import com.dataprocess.bods.vo.QueryDefinitionVO;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryDefinitionBusiness.
 */
public final class QueryDefinitionBusiness {

    /**
     * Validate query definition.
     *
     * @param queryDefinitionVO the query definition vo
     * @return true, if successful
     * @throws BODSException the bODS exception
     */
    public boolean validateQueryDefinition(QueryDefinitionVO queryDefinitionVO) throws BODSException {
        boolean hasCompleted = false;
        QueryDefinitionDAO queryDefinitionDAO = null;
        try {
            queryDefinitionDAO = new QueryDefinitionDAO();
            hasCompleted = queryDefinitionDAO.validateQueryDefinition(queryDefinitionVO);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionBusiness", "validateQueryDefinition", exception.getMessage());
        }
        return hasCompleted;
    }

    /**
     * Save query definition.
     *
     * @param queryDefinitionVO the query definition vo
     * @return true, if successful
     * @throws BODSException the bODS exception
     */
    public QueryDefinitionVO saveQueryDefinition(QueryDefinitionVO queryDefinitionVO) throws BODSException {
        BytesUtil bytesUtil = null;
        SpringBeanUtils beanUtils = null;
        QueryDefinitionEO queryDefinitionEO = null;
        QueryDefinitionDAO queryDefinitionDAO = null;
        try {
            bytesUtil = new BytesUtil();
            beanUtils = new SpringBeanUtils();
            queryDefinitionEO = new QueryDefinitionEO();
            queryDefinitionDAO = new QueryDefinitionDAO();
            queryDefinitionEO =
                (QueryDefinitionEO) beanUtils.populateToEntityObject(queryDefinitionVO, queryDefinitionEO);
            queryDefinitionEO.setObject(bytesUtil.toByteArray(queryDefinitionVO));
            queryDefinitionEO = queryDefinitionDAO.saveQueryDefinition(queryDefinitionEO);
            queryDefinitionVO.setSourceConfiguratorId(queryDefinitionEO.getSourceConfiguratorId());
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionBusiness", "saveQueryDefinition", exception.getMessage());
        }
        return queryDefinitionVO;
    }

    /**
     * Gets the source config connection list.
     *
     * @return the source config connection list
     * @throws BODSException the bODS exception
     */
    public ArrayList<QueryDefinitionVO> getSourceConfigConnectionList() throws BODSException {
        QueryDefinitionDAO queryDefinitionDAO = null;
        ArrayList<QueryDefinitionVO> sourceConfigConnectionList = null;
        try {
            queryDefinitionDAO = new QueryDefinitionDAO();
            sourceConfigConnectionList = queryDefinitionDAO.getSourceConfigConnectionList();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionBusiness", "getSourceConfigConnectionList", exception.getMessage());
        }
        return sourceConfigConnectionList;
    }

    /**
     * Fetch query definition list.
     *
     * @param queryDefinitionVO the query definition vo
     * @throws BODSException the bODS exception
     */
    public void fetchQueryDefinitionList(QueryDefinitionVO queryDefinitionVO) throws BODSException {
        QueryDefinitionDAO queryDefinitionDAO = null;
        try {
            queryDefinitionDAO = new QueryDefinitionDAO();
            queryDefinitionDAO.fetchQueryDefinitionList(queryDefinitionVO);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionBusiness", "getSourceConfigConnectionList", exception.getMessage());
        }
    }

    /**
     * Fetch query definition details.
     *
     * @param queryDefinitionVO the query definition vo
     * @return the query definition vo
     * @throws BODSException the bODS exception
     */
    public QueryDefinitionVO fetchQueryDefinitionDetails(QueryDefinitionVO queryDefinitionVO) throws BODSException {
        BytesUtil bytesUtil = null;
        QueryDefinitionEO queryDefinitionEO = null;
        QueryDefinitionDAO queryDefinitionDAO = null;
        try {
            queryDefinitionDAO = new QueryDefinitionDAO();
            bytesUtil = new BytesUtil();
            queryDefinitionEO = queryDefinitionDAO.fetchQueryDefinitionDetails(queryDefinitionVO);
            queryDefinitionVO = (QueryDefinitionVO) bytesUtil.toObject(queryDefinitionEO.getObject());
            if (queryDefinitionVO.getSourceConfiguratorLineVOSet() != null
                && queryDefinitionVO.getSourceConfiguratorLineVOSet().size() > 0) {
                queryDefinitionVO.setQueryDefinitionLineVOList(new ArrayList<QueryDefinitionLineVO>(queryDefinitionVO
                    .getSourceConfiguratorLineVOSet()));
            }
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionBusiness", "fetchQueryDefinitionDetails", exception.getMessage());
        }
        return queryDefinitionVO;
    }

    /**
     * Show data table grid.
     *
     * @param queryDefinitionVO the query definition vo
     * @return the query definition vo
     * @throws BODSException the bODS exception
     */
    public QueryDefinitionVO showDataTableGrid(QueryDefinitionVO queryDefinitionVO) throws BODSException {
        String query = "";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        QueryDefinitionDataVO queryDefinitionDataVO = null;
        QueryDefinitionDataVO parentQueryDefinitionDataVO = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        List<QueryDefinitionDataVO> queryDefinitionDataVOList = null;
        List<QueryDefinitionDataVO> parentQueryDefinitionDataVOList = null;
        try {
            parentQueryDefinitionDataVOList = new ArrayList<QueryDefinitionDataVO>();
            jdbcConnectionManager = new JDBCConnectionManager();
            if (jdbcConnectionManager.getJDBCConnection()) {
                connection = jdbcConnectionManager.getConnection();
                query = queryDefinitionVO.getSqlQuery().replaceAll(";", "");
                ps = connection.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    parentQueryDefinitionDataVO = new QueryDefinitionDataVO();
                    queryDefinitionDataVOList = new ArrayList<QueryDefinitionDataVO>();
                    for (QueryDefinitionLineVO queryDefinitionLineVO : queryDefinitionVO.getQueryDefinitionLineVOList()) {
                        queryDefinitionDataVO = new QueryDefinitionDataVO();
                        if ("String".equalsIgnoreCase(queryDefinitionLineVO.getDataType())) {
                            if (rs.getString(queryDefinitionLineVO.getColumnName()) != null) {
                                queryDefinitionDataVO.setDataValue(rs.getString(queryDefinitionLineVO.getColumnName()));
                            }
                            getConversionColumnValue(queryDefinitionLineVO, queryDefinitionDataVO);
                        } else if ("Number".equalsIgnoreCase(queryDefinitionLineVO.getDataType())) {
                            if (rs.getInt(queryDefinitionLineVO.getColumnName()) > 0
                                && rs.getString(queryDefinitionLineVO.getColumnName()) != null) {
                                queryDefinitionDataVO.setDataValue(String.valueOf(rs.getInt(queryDefinitionLineVO
                                    .getColumnName())));
                            }
                            getConversionColumnValue(queryDefinitionLineVO, queryDefinitionDataVO);
                        } else if ("Date".equalsIgnoreCase(queryDefinitionLineVO.getDataType())) {
                            if (rs.getDate(queryDefinitionLineVO.getColumnName()) != null) {
                                queryDefinitionDataVO.setDataValue(String.valueOf(rs.getDate(queryDefinitionLineVO
                                    .getColumnName())));
                            }
                            getConversionColumnValue(queryDefinitionLineVO, queryDefinitionDataVO);
                        } else if ("Timestamp".equals(queryDefinitionLineVO.getDataType())) {
                            if (rs.getTimestamp(queryDefinitionLineVO.getColumnName()) != null) {
                                queryDefinitionDataVO.setDataValue(String.valueOf(rs.getTimestamp(queryDefinitionLineVO
                                    .getColumnName())));
                            }
                            getConversionColumnValue(queryDefinitionLineVO, queryDefinitionDataVO);
                        }
                        queryDefinitionDataVOList.add(queryDefinitionDataVO);

                    }
                    parentQueryDefinitionDataVO.setQueryDefinitionDataVOList(queryDefinitionDataVOList);
                    if (parentQueryDefinitionDataVOList.size() > 0) {
                        queryDefinitionVO.getQueryDefinitionDataVOList().add(parentQueryDefinitionDataVO);
                    } else {
                        parentQueryDefinitionDataVOList.add(parentQueryDefinitionDataVO);
                        queryDefinitionVO.setQueryDefinitionDataVOList(parentQueryDefinitionDataVOList);
                    }
                }
                queryDefinitionVO.setQueryDefinitionDataVOList(parentQueryDefinitionDataVOList);
            }
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionBusiness", "fetchQueryDefinitionList", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
        return queryDefinitionVO;
    }

    /**
     * Gets the conversion column value.
     *
     * @param queryDefinitionLineVO the query definition line vo
     * @param queryDefinitionDataVO the query definition data vo
     * @return the conversion column value
     * @throws BODSException the bODS exception
     */
    private void getConversionColumnValue(QueryDefinitionLineVO queryDefinitionLineVO,
        QueryDefinitionDataVO queryDefinitionDataVO) throws BODSException {
        try {
            if (queryDefinitionLineVO.getConversionColumn() != null
                && !queryDefinitionLineVO.getConversionColumn().equals("")) {
                queryDefinitionDataVO.setUniqueFlag(queryDefinitionLineVO.getConversionColumn());
            } else {
                queryDefinitionDataVO.setUniqueFlag("N");
            }
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionBusiness", "fetchQueryDefinitionList", exception.getMessage());
        }
    }
}
