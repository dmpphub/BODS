package com.dataprocess.bods.business;

import java.util.ArrayList;

import com.dataprocess.bods.dao.QueryDefinitionDAO;
import com.dataprocess.bods.entity.QueryDefinitionEO;
import com.dataprocess.bods.util.BytesUtil;
import com.dataprocess.bods.util.SpringBeanUtils;
import com.dataprocess.bods.vo.QueryDefinitionLineVO;
import com.dataprocess.bods.vo.QueryDefinitionVO;

/**
 * The Class QueryDefinitionBusiness.
 */
public class QueryDefinitionBusiness {

    /**
     * Validate query definition.
     *
     * @param queryDefinitionVO the query definition vo
     * @return true, if successful
     */
    public boolean validateQueryDefinition(QueryDefinitionVO queryDefinitionVO) {
        QueryDefinitionDAO queryDefinitionDAO = null;
        boolean hasCompleted = false;

        try {
            queryDefinitionDAO = new QueryDefinitionDAO();
            hasCompleted = queryDefinitionDAO.validateQueryDefinition(queryDefinitionVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasCompleted;
    }

    /**
     * Save query definition.
     *
     * @param queryDefinitionVO the query definition vo
     * @return true, if successful
     */
    public QueryDefinitionVO saveQueryDefinition(QueryDefinitionVO queryDefinitionVO) {
        QueryDefinitionDAO queryDefinitionDAO = null;
        SpringBeanUtils beanUtils = null;
        BytesUtil bytesUtil = null;
        QueryDefinitionEO queryDefinitionEO = null;
        
        try {
            beanUtils = new SpringBeanUtils();
            queryDefinitionDAO = new QueryDefinitionDAO();
            queryDefinitionEO = new QueryDefinitionEO();
            bytesUtil = new BytesUtil();
            queryDefinitionEO.setObject(bytesUtil.toByteArray(queryDefinitionVO));
            queryDefinitionEO =
                (QueryDefinitionEO) beanUtils.populateToEntityObject(queryDefinitionVO, queryDefinitionEO);
            queryDefinitionEO = queryDefinitionDAO.saveQueryDefinition(queryDefinitionEO);
           	queryDefinitionVO.setSourceConfiguratorId(queryDefinitionEO.getSourceConfiguratorId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryDefinitionVO;
    }

    /**
     * Gets the source config connection list.
     *
     * @return the source config connection list
     */
    public ArrayList<QueryDefinitionVO> getSourceConfigConnectionList() {
        QueryDefinitionDAO queryDefinitionDAO = null;
        ArrayList<QueryDefinitionVO> sourceConfigConnectionList = null;

        try {
            queryDefinitionDAO = new QueryDefinitionDAO();
            sourceConfigConnectionList = queryDefinitionDAO.getSourceConfigConnectionList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sourceConfigConnectionList;
    }

    /**
     * Fetch query definition list.
     *
     * @param queryDefinitionVO the query definition vo
     */
    public void fetchQueryDefinitionList(QueryDefinitionVO queryDefinitionVO) {
        QueryDefinitionDAO queryDefinitionDAO = null;

        try {
            queryDefinitionDAO = new QueryDefinitionDAO();
            queryDefinitionDAO.fetchQueryDefinitionList(queryDefinitionVO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetch query definition details.
     *
     * @param queryDefinitionVO the query definition vo
     * @return the query definition vo
     */
    public QueryDefinitionVO fetchQueryDefinitionDetails(QueryDefinitionVO queryDefinitionVO) {
        QueryDefinitionEO queryDefinitionEO = null;
        QueryDefinitionDAO queryDefinitionDAO = null;
        BytesUtil bytesUtil = null;
        
        try {
            queryDefinitionDAO = new QueryDefinitionDAO();
            bytesUtil = new BytesUtil();
            queryDefinitionEO = queryDefinitionDAO.fetchQueryDefinitionDetails(queryDefinitionVO);
            queryDefinitionVO = (QueryDefinitionVO) bytesUtil.toObject(queryDefinitionEO.getObject());
            /*queryDefinitionVO =
                (QueryDefinitionVO) new SpringBeanUtils().populateToDTOObject(queryDefinitionEO, queryDefinitionVO);*/

            if (queryDefinitionVO.getSourceConfiguratorLineVOSet() != null && queryDefinitionVO.getSourceConfiguratorLineVOSet().size() > 0) {
            	queryDefinitionVO.setQueryDefinitionLineVOList(new ArrayList<QueryDefinitionLineVO>(queryDefinitionVO.getSourceConfiguratorLineVOSet()));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryDefinitionVO;
    }
}
