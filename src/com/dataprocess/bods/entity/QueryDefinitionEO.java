package com.dataprocess.bods.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryDefinitionEO.
 */
@Entity
@Table(name = "BODS_SOURCE_CFG")
public final class QueryDefinitionEO {

    /** The source configurator id. */
    @SequenceGenerator(name = "generator", sequenceName = "SOURCE_CFG_ID_SEQ")
    @Id
    @Column(name = "SOURCE_CFG_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    private int sourceConfiguratorId;

    /** The source config connection id. */
    @Column(name = "CONNECTION_ID")
    private int sourceConfigConnectionId;

    /** The source config name. */
    @Column(name = "SOURCE_CFG_NAME")
    private String sourceConfigName;

    /** The display name. */
    @Column(name = "DISPLAY_NAME")
    private String displayName;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The sql query. */
    @Column(name = "SQL_QUERY")
    private String sqlQuery;

    /** The source configurator line eo set. */
    @JoinColumn(name = "SOURCE_CFG_ID")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Set<QueryDefinitionLineEO> sourceConfiguratorLineEOSet;

    /** The object. */
    @Column(name = "SOURCE_CFG_OBJECT")
    private byte[] object;

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
     * Gets the source configurator line eo set.
     *
     * @return the source configurator line eo set
     */
    public Set<QueryDefinitionLineEO> getSourceConfiguratorLineEOSet() {
        return sourceConfiguratorLineEOSet;
    }

    /**
     * Sets the source configurator line eo set.
     *
     * @param sourceConfiguratorLineEOSet the new source configurator line eo set
     */
    public void setSourceConfiguratorLineEOSet(Set<QueryDefinitionLineEO> sourceConfiguratorLineEOSet) {
        this.sourceConfiguratorLineEOSet = sourceConfiguratorLineEOSet;
    }

    /**
     * Gets the object.
     *
     * @return the object
     */
    public byte[] getObject() {
        return object;
    }

    /**
     * Sets the object.
     *
     * @param object the new object
     */
    public void setObject(byte[] object) {
        this.object = object;
    }
}
