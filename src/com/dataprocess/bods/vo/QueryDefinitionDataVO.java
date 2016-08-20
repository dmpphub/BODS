package com.dataprocess.bods.vo;

import java.util.List;

public class QueryDefinitionDataVO {
	
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