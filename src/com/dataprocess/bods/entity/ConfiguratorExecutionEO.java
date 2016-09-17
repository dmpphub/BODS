package com.dataprocess.bods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The Class ConfiguratorExecutionEO.
 */
@Entity
@Table(name = "BODS_CONFIGURATOR_EXEC")
public final class ConfiguratorExecutionEO {

    /** The configurator exec id. */
    @SequenceGenerator(name = "generator", sequenceName = "CONFIGURATOR_EXEC_ID_SEQ")
    @Id
    @Column(name = "CONFIGURATOR_EXEC_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    private int configuratorExecId;

    /** The configurator id. */
    @Column(name = "CONFIGURATOR_ID")
    private int configuratorId;

    /** The current status. */
    @Column(name = "STATUS")
    private String currentStatus;

    /**
     * Gets the configurator exec id.
     *
     * @return the configurator exec id
     */
    public int getConfiguratorExecId() {
        return configuratorExecId;
    }

    /**
     * Sets the configurator exec id.
     *
     * @param configuratorExecId the new configurator exec id
     */
    public void setConfiguratorExecId(int configuratorExecId) {
        this.configuratorExecId = configuratorExecId;
    }

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
     * Gets the current status.
     *
     * @return the current status
     */
    public String getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Sets the current status.
     *
     * @param currentStatus the new current status
     */
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

}
