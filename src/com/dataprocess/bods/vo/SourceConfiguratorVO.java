package com.dataprocess.bods.vo;

import java.util.List;
import java.util.Set;

import com.dataprocess.bods.util.EntityProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class SourceConfiguratorVO.
 */
public class SourceConfiguratorVO {

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

    /** The source configurator line vo list. */
    private List<SourceConfiguratorLineVO> sourceConfiguratorLineVOList;

    /** The source configurator line vo set. */
    @EntityProperty(columnName = "sourceConfiguratorLineEOSet", entity = "com.dataprocess.bods.entity.SourceConfiguratorLineEO")
    private Set<SourceConfiguratorLineVO> sourceConfiguratorLineVOSet;

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
     * Gets the source configurator line vo list.
     *
     * @return the source configurator line vo list
     */
    public List<SourceConfiguratorLineVO> getSourceConfiguratorLineVOList() {
        return sourceConfiguratorLineVOList;
    }

    /**
     * Sets the source configurator line vo list.
     *
     * @param sourceConfiguratorLineVOList the new source configurator line vo list
     */
    public void setSourceConfiguratorLineVOList(List<SourceConfiguratorLineVO> sourceConfiguratorLineVOList) {
        this.sourceConfiguratorLineVOList = sourceConfiguratorLineVOList;
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
     * Gets the source configurator line vo set.
     *
     * @return the source configurator line vo set
     */
    public Set<SourceConfiguratorLineVO> getSourceConfiguratorLineVOSet() {
        return sourceConfiguratorLineVOSet;
    }

    /**
     * Sets the source configurator line vo set.
     *
     * @param sourceConfiguratorLineVOSet the new source configurator line vo set
     */
    public void setSourceConfiguratorLineVOSet(Set<SourceConfiguratorLineVO> sourceConfiguratorLineVOSet) {
        this.sourceConfiguratorLineVOSet = sourceConfiguratorLineVOSet;
    }
}
