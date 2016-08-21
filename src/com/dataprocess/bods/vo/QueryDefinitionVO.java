package com.dataprocess.bods.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dataprocess.bods.util.EntityProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryDefinitionVO.
 */
public class QueryDefinitionVO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8471253148753759944L;

    /** The source configurator id. */
    @EntityProperty(columnName = "sourceConfiguratorId")
    private int sourceConfiguratorId;

    /** The source config connection id. */
    @EntityProperty(columnName = "sourceConfigConnectionId")
    private int sourceConfigConnectionId;

    /** The source config name id. */
    @EntityProperty(columnName = "sourceConfigNameId")
    private int sourceConfigNameId;

    /** The source config connection. */
    @EntityProperty(columnName = "sourceConfigConnection")
    private String sourceConfigConnection;

    /** The source config name. */
    @EntityProperty(columnName = "sourceConfigName")
    private String sourceConfigName;

    /** The display name. */
    @EntityProperty(columnName = "displayName")
    private String displayName;

    /** The description. */
    @EntityProperty(columnName = "description")
    private String description;

    /** The sql query. */
    @EntityProperty(columnName = "sqlQuery")
    private String sqlQuery;

    /** The query validated flag. */
    private String queryValidatedFlag;

    /** The save flag. */
    private String saveFlag;

    /** The source config connection list. */
    private ArrayList<QueryDefinitionVO> sourceConfigConnectionList;

    /** The query definition line vo list. */
    private List<QueryDefinitionLineVO> queryDefinitionLineVOList;

    /** The query definition data vo list. */
    private List<QueryDefinitionDataVO> queryDefinitionDataVOList;

    /** The source configurator line vo set. */
    @EntityProperty(columnName = "sourceConfiguratorLineEOSet", entity = "com.dataprocess.bods.entity.QueryDefinitionLineEO")
    private Set<QueryDefinitionLineVO> sourceConfiguratorLineVOSet;

    /** The query definition vo list. */
    private List<QueryDefinitionVO> queryDefinitionVOList;

    /**
     * Gets the source configurator line vo set.
     *
     * @return the source configurator line vo set
     */
    public Set<QueryDefinitionLineVO> getSourceConfiguratorLineVOSet() {
        return sourceConfiguratorLineVOSet;
    }

    /**
     * Sets the source configurator line vo set.
     *
     * @param sourceConfiguratorLineVOSet the new source configurator line vo set
     */
    public void setSourceConfiguratorLineVOSet(Set<QueryDefinitionLineVO> sourceConfiguratorLineVOSet) {
        this.sourceConfiguratorLineVOSet = sourceConfiguratorLineVOSet;
    }

    /**
     * Gets the source config connection.
     *
     * @return the source config connection
     */
    public String getSourceConfigConnection() {
        return sourceConfigConnection;
    }

    /**
     * Sets the source config connection.
     *
     * @param sourceConfigConnection the new source config connection
     */
    public void setSourceConfigConnection(String sourceConfigConnection) {
        this.sourceConfigConnection = sourceConfigConnection;
    }

    /**
     * Gets the source config connection id.
     *
     * @return the source config connection id
     */
    public int getSourceConfigConnectionId() {
        return sourceConfigConnectionId;
    }

    /**
     * Sets the source config connection id.
     *
     * @param sourceConfigConnectionId the new source config connection id
     */
    public void setSourceConfigConnectionId(int sourceConfigConnectionId) {
        this.sourceConfigConnectionId = sourceConfigConnectionId;
    }

    /**
     * Gets the source config name.
     *
     * @return the source config name
     */
    public String getSourceConfigName() {
        return sourceConfigName;
    }

    /**
     * Sets the source config name.
     *
     * @param sourceConfigName the new source config name
     */
    public void setSourceConfigName(String sourceConfigName) {
        this.sourceConfigName = sourceConfigName;
    }

    /**
     * Gets the source config name id.
     *
     * @return the source config name id
     */
    public int getSourceConfigNameId() {
        return sourceConfigNameId;
    }

    /**
     * Sets the source config name id.
     *
     * @param sourceConfigNameId the new source config name id
     */
    public void setSourceConfigNameId(int sourceConfigNameId) {
        this.sourceConfigNameId = sourceConfigNameId;
    }

    /**
     * Gets the display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name.
     *
     * @param displayName the new display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the sql query.
     *
     * @return the sql query
     */
    public String getSqlQuery() {
        return sqlQuery;
    }

    /**
     * Sets the sql query.
     *
     * @param sqlQuery the new sql query
     */
    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    /**
     * Gets the source configurator id.
     *
     * @return the source configurator id
     */
    public int getSourceConfiguratorId() {
        return sourceConfiguratorId;
    }

    /**
     * Sets the source configurator id.
     *
     * @param sourceConfiguratorId the new source configurator id
     */
    public void setSourceConfiguratorId(int sourceConfiguratorId) {
        this.sourceConfiguratorId = sourceConfiguratorId;
    }

    /**
     * Gets the query definition line vo list.
     *
     * @return the query definition line vo list
     */
    public List<QueryDefinitionLineVO> getQueryDefinitionLineVOList() {
        return queryDefinitionLineVOList;
    }

    /**
     * Sets the query definition line vo list.
     *
     * @param queryDefinitionLineVOList the new query definition line vo list
     */
    public void setQueryDefinitionLineVOList(List<QueryDefinitionLineVO> queryDefinitionLineVOList) {
        this.queryDefinitionLineVOList = queryDefinitionLineVOList;
    }

    /**
     * Gets the query validated flag.
     *
     * @return the query validated flag
     */
    public String getQueryValidatedFlag() {
        return queryValidatedFlag;
    }

    /**
     * Sets the query validated flag.
     *
     * @param queryValidatedFlag the new query validated flag
     */
    public void setQueryValidatedFlag(String queryValidatedFlag) {
        this.queryValidatedFlag = queryValidatedFlag;
    }

    /**
     * Gets the save flag.
     *
     * @return the save flag
     */
    public String getSaveFlag() {
        return saveFlag;
    }

    /**
     * Sets the save flag.
     *
     * @param saveFlag the new save flag
     */
    public void setSaveFlag(String saveFlag) {
        this.saveFlag = saveFlag;
    }

    /**
     * Gets the source config connection list.
     *
     * @return the source config connection list
     */
    public ArrayList<QueryDefinitionVO> getSourceConfigConnectionList() {
        return sourceConfigConnectionList;
    }

    /**
     * Sets the source config connection list.
     *
     * @param sourceConfigConnectionList the new source config connection list
     */
    public void setSourceConfigConnectionList(ArrayList<QueryDefinitionVO> sourceConfigConnectionList) {
        this.sourceConfigConnectionList = sourceConfigConnectionList;
    }

    /**
     * Gets the query definition vo list.
     *
     * @return the query definition vo list
     */
    public List<QueryDefinitionVO> getQueryDefinitionVOList() {
        return queryDefinitionVOList;
    }

    /**
     * Sets the query definition vo list.
     *
     * @param queryDefinitionVOList the new query definition vo list
     */
    public void setQueryDefinitionVOList(List<QueryDefinitionVO> queryDefinitionVOList) {
        this.queryDefinitionVOList = queryDefinitionVOList;
    }

    /**
     * Gets the query definition data vo list.
     *
     * @return the query definition data vo list
     */
    public List<QueryDefinitionDataVO> getQueryDefinitionDataVOList() {
        return queryDefinitionDataVOList;
    }

    /**
     * Sets the query definition data vo list.
     *
     * @param queryDefinitionDataVOList the new query definition data vo list
     */
    public void setQueryDefinitionDataVOList(List<QueryDefinitionDataVO> queryDefinitionDataVOList) {
        this.queryDefinitionDataVOList = queryDefinitionDataVOList;
    }
}
