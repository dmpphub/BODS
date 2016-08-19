package com.dataprocess.bods.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

/**
 * The Class ConfiguratorEO.
 */
@Entity
@Table(name = "BODS_CONFIGURATOR_CFG")
public class ConfiguratorEO {

    /** The configurator id. */
    @SequenceGenerator(name = "generator", sequenceName = "CONFIGURATOR_CFG_ID_SEQ")
    @Id
    @Column(name = "CONFIGURATOR_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    private int configuratorId;

    /** The configurator connection id. */
    @Column(name = "CONFIGURATOR_CONN_ID")
    private int configuratorConnectionId;

    /** The configurator name. */
    @Column(name = "CONFIGURATOR_NAME")
    private String configuratorName;

    /** The configurator connection name. */
    @Formula("(SELECT CONN.CONNECTION_NAME FROM BODS_CONNECTION_CFG CONN WHERE CONN.CONNECTION_ID = CONFIGURATOR_CONN_ID)")
    private String configuratorConnectionName;

    /** The display name. */
    @Column(name = "DISPLAY_NAME")
    private String displayName;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The redundant reducer. */
    @Column(name = "REDUNDANTREDUCER")
    private String redundantReducer;

    /** The conversion. */
    @Column(name = "CONVERSION")
    private String conversion;

    /** The source configuration connection id. */
    @Column(name = "SOURCE_CONFIGURATOR_CONN_ID")
    private int sourceConfigurationConnectionId;

    /** The source configuration id. */
    @Column(name = "SOURCE_CONFIGURATOR_ID")
    private int sourceConfigurationId;

    /** The configurator binaries eo. */
    @JoinColumn(name = "CONFIGURATOR_ID")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private ConfiguratorBinariesEO configuratorBinariesEO;

    /**
     * Gets the configurator id.
     * 
     * @return the configurator id
     */
    public int getConfiguratorId() {
        return configuratorId;
    }

    /**
     * Sets the configurator id.
     * 
     * @param configuratorId the new configurator id
     */
    public void setConfiguratorId(int configuratorId) {
        this.configuratorId = configuratorId;
    }

    /**
     * Gets the configurator connection id.
     * 
     * @return the configurator connection id
     */
    public int getConfiguratorConnectionId() {
        return configuratorConnectionId;
    }

    /**
     * Sets the configurator connection id.
     * 
     * @param configuratorConnectionId the new configurator connection id
     */
    public void setConfiguratorConnectionId(int configuratorConnectionId) {
        this.configuratorConnectionId = configuratorConnectionId;
    }

    /**
     * Gets the configurator name.
     * 
     * @return the configurator name
     */
    public String getConfiguratorName() {
        return configuratorName;
    }

    /**
     * Sets the configurator name.
     * 
     * @param configuratorName the new configurator name
     */
    public void setConfiguratorName(String configuratorName) {
        this.configuratorName = configuratorName;
    }

    /**
     * Gets the configurator connection name.
     * 
     * @return the configurator connection name
     */
    public String getConfiguratorConnectionName() {
        return configuratorConnectionName;
    }

    /**
     * Sets the configurator connection name.
     * 
     * @param configuratorConnectionName the new configurator connection name
     */
    public void setConfiguratorConnectionName(String configuratorConnectionName) {
        this.configuratorConnectionName = configuratorConnectionName;
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
     * Gets the redundant reducer.
     * 
     * @return the redundant reducer
     */
    public String getRedundantReducer() {
        return redundantReducer;
    }

    /**
     * Sets the redundant reducer.
     * 
     * @param redundantReducer the new redundant reducer
     */
    public void setRedundantReducer(String redundantReducer) {
        this.redundantReducer = redundantReducer;
    }

    /**
     * Gets the conversion.
     * 
     * @return the conversion
     */
    public String getConversion() {
        return conversion;
    }

    /**
     * Sets the conversion.
     * 
     * @param conversion the new conversion
     */
    public void setConversion(String conversion) {
        this.conversion = conversion;
    }

    /**
     * Gets the source configuration connection id.
     *
     * @return the source configuration connection id
     */
    public int getSourceConfigurationConnectionId() {
        return sourceConfigurationConnectionId;
    }

    /**
     * Sets the source configuration connection id.
     *
     * @param sourceConfigurationConnectionId the new source configuration connection id
     */
    public void setSourceConfigurationConnectionId(int sourceConfigurationConnectionId) {
        this.sourceConfigurationConnectionId = sourceConfigurationConnectionId;
    }

    /**
     * Gets the source configuration id.
     *
     * @return the source configuration id
     */
    public int getSourceConfigurationId() {
        return sourceConfigurationId;
    }

    /**
     * Sets the source configuration id.
     *
     * @param sourceConfigurationId the new source configuration id
     */
    public void setSourceConfigurationId(int sourceConfigurationId) {
        this.sourceConfigurationId = sourceConfigurationId;
    }

    /**
     * Gets the configurator binaries eo.
     *
     * @return the configurator binaries eo
     */
    public ConfiguratorBinariesEO getConfiguratorBinariesEO() {
        return configuratorBinariesEO;
    }

    /**
     * Sets the configurator binaries eo.
     *
     * @param configuratorBinariesEO the new configurator binaries eo
     */
    public void setConfiguratorBinariesEO(ConfiguratorBinariesEO configuratorBinariesEO) {
        this.configuratorBinariesEO = configuratorBinariesEO;
    }

}
