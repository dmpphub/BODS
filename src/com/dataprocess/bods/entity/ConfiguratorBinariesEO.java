package com.dataprocess.bods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The Class ConfiguratorBinariesEO.
 */
@Entity
@Table(name = "BODS_CFG_BINARIES")
public final class ConfiguratorBinariesEO {

    /** The configurator id. */
    @SequenceGenerator(name = "generator", sequenceName = "BODS_CFG_BINARIES_ID_SEQ")
    @Id
    @Column(name = "CFG_BINARIES_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    private int configuratorBinaryId;

    /** The configurator id. */
    @Column(name = "CONFIGURATOR_ID")
    private int configuratorId;

    /** The object. */
    @Column(name = "OBJECT")
    private byte[] object;

    /**
     * Gets the configurator id.
     *
     * @return the configurator id
     */
    public int getConfiguratorId() {
        return configuratorId;
    }

    /**
     * Gets the configurator binary id.
     *
     * @return the configurator binary id
     */
    public int getConfiguratorBinaryId() {
        return configuratorBinaryId;
    }

    /**
     * Sets the configurator binary id.
     *
     * @param configuratorBinaryId the new configurator binary id
     */
    public void setConfiguratorBinaryId(int configuratorBinaryId) {
        this.configuratorBinaryId = configuratorBinaryId;
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
