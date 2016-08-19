package com.dataprocess.bods.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * The Class ConfiguratorObjectEO.
 */
@Entity
@Table(name = "BODS_CONFIG_OBJECT")
@PrimaryKeyJoinColumn(name = "APP_CONNECTION_ID")
public class ConfiguratorObjectEO extends ConfiguratorEO {

    /** The configurator object id. */
    private int configuratorObjectId;

    /** The configurator id. */
    private int configuratorId;

    /** The configurator object. */
    private byte[] configuratorObject;

    /**
     * Gets the configurator object id.
     *
     * @return the configurator object id
     */
    public int getConfiguratorObjectId() {
        return configuratorObjectId;
    }

    /**
     * Sets the configurator object id.
     *
     * @param configuratorObjectId the new configurator object id
     */
    public void setConfiguratorObjectId(int configuratorObjectId) {
        this.configuratorObjectId = configuratorObjectId;
    }

    /* (non-Javadoc)
     * @see com.dataprocess.bods.entity.ConfiguratorEO#getConfiguratorId()
     */
    public int getConfiguratorId() {
        return configuratorId;
    }

    /* (non-Javadoc)
     * @see com.dataprocess.bods.entity.ConfiguratorEO#setConfiguratorId(int)
     */
    public void setConfiguratorId(int configuratorId) {
        this.configuratorId = configuratorId;
    }

    /**
     * Gets the configurator object.
     *
     * @return the configurator object
     */
    public byte[] getConfiguratorObject() {
        return configuratorObject;
    }

    /**
     * Sets the configurator object.
     *
     * @param configuratorObject the new configurator object
     */
    public void setConfiguratorObject(byte[] configuratorObject) {
        this.configuratorObject = configuratorObject;
    }

}
