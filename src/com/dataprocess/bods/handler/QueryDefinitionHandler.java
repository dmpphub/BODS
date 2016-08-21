/*
 * 
 */
package com.dataprocess.bods.handler;

import java.util.ArrayList;

import com.dataprocess.bods.business.QueryDefinitionBusiness;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.vo.QueryDefinitionVO;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryDefinitionHandler.
 */
public final class QueryDefinitionHandler {

    /**
     * Validate query definition.
     *
     * @param queryDefinitionVO the query definition vo
     * @return true, if successful
     * @throws BODSException the bODS exception
     */
    public boolean validateQueryDefinition(QueryDefinitionVO queryDefinitionVO) throws BODSException {
        boolean hasCompleted = false;
        QueryDefinitionBusiness queryDefinitionBusiness = null;
        try {
            queryDefinitionBusiness = new QueryDefinitionBusiness();
            hasCompleted = queryDefinitionBusiness.validateQueryDefinition(queryDefinitionVO);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionHandler", "validateQueryDefinition", exception.getMessage());
        }
        return hasCompleted;
    }

    /**
     * Save query definition.
     *
     * @param queryDefinitionVO the query definition vo
     * @return the query definition vo
     * @throws BODSException the bODS exception
     */
    public QueryDefinitionVO saveQueryDefinition(QueryDefinitionVO queryDefinitionVO) throws BODSException {
        QueryDefinitionBusiness business = null;
        try {
            business = new QueryDefinitionBusiness();
            queryDefinitionVO = business.saveQueryDefinition(queryDefinitionVO);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionHandler", "validateQueryDefinition", exception.getMessage());
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
        QueryDefinitionBusiness business = null;
        ArrayList<QueryDefinitionVO> sourceConfigConnectionList = null;
        try {
            business = new QueryDefinitionBusiness();
            sourceConfigConnectionList = business.getSourceConfigConnectionList();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionHandler", "getSourceConfigConnectionList", exception.getMessage());
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
        QueryDefinitionBusiness business = null;
        try {
            business = new QueryDefinitionBusiness();
            business.fetchQueryDefinitionList(queryDefinitionVO);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionHandler", "fetchQueryDefinitionList", exception.getMessage());
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
        QueryDefinitionBusiness business = null;
        try {
            business = new QueryDefinitionBusiness();
            queryDefinitionVO = business.fetchQueryDefinitionDetails(queryDefinitionVO);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionHandler", "fetchQueryDefinitionList", exception.getMessage());
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
        QueryDefinitionBusiness business = null;
        try {
            business = new QueryDefinitionBusiness();
            queryDefinitionVO = business.showDataTableGrid(queryDefinitionVO);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("QueryDefinitionHandler", "fetchQueryDefinitionList", exception.getMessage());
        }
        return queryDefinitionVO;
    }
}
