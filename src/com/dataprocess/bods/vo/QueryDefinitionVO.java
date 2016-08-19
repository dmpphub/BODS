package com.dataprocess.bods.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dataprocess.bods.util.EntityProperty;

public class QueryDefinitionVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8471253148753759944L;

	@EntityProperty(columnName="sourceConfiguratorId")
	private int sourceConfiguratorId;
	
	@EntityProperty(columnName="sourceConfigConnectionId")
	private int sourceConfigConnectionId;
	
	@EntityProperty(columnName="sourceConfigNameId")
	private int sourceConfigNameId;
	
	@EntityProperty(columnName="sourceConfigConnection")
	private String sourceConfigConnection;
	
	@EntityProperty(columnName="sourceConfigName")
	private String sourceConfigName;
	
	@EntityProperty(columnName="displayName")
	private String displayName;
	
	@EntityProperty(columnName="description")
	private String description;
	
	@EntityProperty(columnName="sqlQuery")
	private String sqlQuery;
	
	private ArrayList<QueryDefinitionVO> sourceConfigConnectionList;
	
	private String queryValidatedFlag;
	private String saveFlag;
	
	private List<QueryDefinitionLineVO> queryDefinitionLineVOList;
	
	@EntityProperty(columnName="sourceConfiguratorLineEOSet", entity="com.dataprocess.bods.entity.QueryDefinitionLineEO")
	private Set<QueryDefinitionLineVO> sourceConfiguratorLineVOSet;
	
	private List<QueryDefinitionVO> queryDefinitionVOList;
	
	public Set<QueryDefinitionLineVO> getSourceConfiguratorLineVOSet() {
		return sourceConfiguratorLineVOSet;
	}

	public void setSourceConfiguratorLineVOSet(Set<QueryDefinitionLineVO> sourceConfiguratorLineVOSet) {
		this.sourceConfiguratorLineVOSet = sourceConfiguratorLineVOSet;
	}

	public String getSourceConfigConnection() {
		return sourceConfigConnection;
	}
	
	public void setSourceConfigConnection(String sourceConfigConnection) {
		this.sourceConfigConnection = sourceConfigConnection;
	}
	
	public int getSourceConfigConnectionId() {
		return sourceConfigConnectionId;
	}
	
	public void setSourceConfigConnectionId(int sourceConfigConnectionId) {
		this.sourceConfigConnectionId = sourceConfigConnectionId;
	}
	
	public String getSourceConfigName() {
		return sourceConfigName;
	}
	
	public void setSourceConfigName(String sourceConfigName) {
		this.sourceConfigName = sourceConfigName;
	}
	
	public int getSourceConfigNameId() {
		return sourceConfigNameId;
	}
	
	public void setSourceConfigNameId(int sourceConfigNameId) {
		this.sourceConfigNameId = sourceConfigNameId;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public int getSourceConfiguratorId() {
		return sourceConfiguratorId;
	}

	public void setSourceConfiguratorId(int sourceConfiguratorId) {
		this.sourceConfiguratorId = sourceConfiguratorId;
	}

	public List<QueryDefinitionLineVO> getQueryDefinitionLineVOList() {
		return queryDefinitionLineVOList;
	}

	public void setQueryDefinitionLineVOList(List<QueryDefinitionLineVO> queryDefinitionLineVOList) {
		this.queryDefinitionLineVOList = queryDefinitionLineVOList;
	}

	public String getQueryValidatedFlag() {
		return queryValidatedFlag;
	}

	public void setQueryValidatedFlag(String queryValidatedFlag) {
		this.queryValidatedFlag = queryValidatedFlag;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public ArrayList<QueryDefinitionVO> getSourceConfigConnectionList() {
		return sourceConfigConnectionList;
	}

	public void setSourceConfigConnectionList(ArrayList<QueryDefinitionVO> sourceConfigConnectionList) {
		this.sourceConfigConnectionList = sourceConfigConnectionList;
	}

	public List<QueryDefinitionVO> getQueryDefinitionVOList() {
		return queryDefinitionVOList;
	}

	public void setQueryDefinitionVOList(List<QueryDefinitionVO> queryDefinitionVOList) {
		this.queryDefinitionVOList = queryDefinitionVOList;
	}
}
