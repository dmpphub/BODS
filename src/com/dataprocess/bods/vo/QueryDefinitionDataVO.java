package com.dataprocess.bods.vo;

import java.io.Serializable;
import java.util.List;

public class QueryDefinitionDataVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5093679475070831768L;

	private String dataValue;

	private List<QueryDefinitionDataVO> queryDefinitionDataVOList;
	
	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public List<QueryDefinitionDataVO> getQueryDefinitionDataVOList() {
		return queryDefinitionDataVOList;
	}

	public void setQueryDefinitionDataVOList(
			List<QueryDefinitionDataVO> queryDefinitionDataVOList) {
		this.queryDefinitionDataVOList = queryDefinitionDataVOList;
	}

}