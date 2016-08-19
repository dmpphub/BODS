package com.dataprocess.bods.handler;

import java.util.ArrayList;

import com.dataprocess.bods.business.QueryDefinitionBusiness;
import com.dataprocess.bods.vo.QueryDefinitionVO;

public class QueryDefinitionHandler {
	
	public boolean validateQueryDefinition (QueryDefinitionVO queryDefinitionVO) {
		QueryDefinitionBusiness queryDefinitionBusiness = null; 
		boolean hasCompleted = false;
		
		try {
			queryDefinitionBusiness = new QueryDefinitionBusiness();
			hasCompleted = queryDefinitionBusiness.validateQueryDefinition(queryDefinitionVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasCompleted;
	}

	public boolean saveQueryDefinition(QueryDefinitionVO queryDefinitionVO) {
		QueryDefinitionBusiness business = null;
		boolean hasCompleted = false;
		
		try {
			business = new QueryDefinitionBusiness();
			hasCompleted = business.saveQueryDefinition(queryDefinitionVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasCompleted;
	}

	public ArrayList<QueryDefinitionVO> getSourceConfigConnectionList() {
		QueryDefinitionBusiness business = null;
		ArrayList<QueryDefinitionVO> sourceConfigConnectionList = null;
		
		try {
			business = new QueryDefinitionBusiness();
			sourceConfigConnectionList = business.getSourceConfigConnectionList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceConfigConnectionList;
	}

	public void fetchQueryDefinitionList(QueryDefinitionVO queryDefinitionVO) {
		QueryDefinitionBusiness business = null;
		
		try {
			business = new QueryDefinitionBusiness();
			business.fetchQueryDefinitionList(queryDefinitionVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public QueryDefinitionVO fetchQueryDefinitionDetails(QueryDefinitionVO queryDefinitionVO) {
		QueryDefinitionBusiness business = null;
		
		try {
			business = new QueryDefinitionBusiness();
			business.fetchQueryDefinitionDetails(queryDefinitionVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryDefinitionVO;
		
	}
}
