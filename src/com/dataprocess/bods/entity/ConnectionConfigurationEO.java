package com.dataprocess.bods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class ConnectionConfigurationEO.
 */
@Entity
@Table(name = "BODS_CONNECTION_CFG")
public final class ConnectionConfigurationEO {

    /** The connection id. */
    @Id
    @Column(name = "CONNECTION_ID")
    private int connectionId;

    /** The port number. */
    @Column(name = "DB_PORT")
    private int portNumber;

    /** The connection name. */
    @Column(name = "CONNECTION_NAME")
    private String connectionName;

    /** The display name. */
    @Column(name = "DISPLAY_NAME")
    private String displayName;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The host name. */
    @Column(name = "HOST_NAME")
    private String hostName;

    /** The service name. */
    @Column(name = "DB_SERVICE_ID")
    private String serviceIdorserviceName;

    /** The user name. */
    @Column(name = "DB_USERNAME")
    private String userName;

    /** The password. */
    @Column(name = "DB_PASSWORD")
    private String password;

    /** The connection mode. */
    @Column(name = "CONNECTION_MODE_TYPE")
    private String connectionMode;

    /**
     * Gets the connection id.
     *
     * @return the connection id
     */
    public int getConnectionId() {
        return connectionId;
    }

    /**
     * Sets the connection id.
     *
     * @param connectionId the new connection id
     */
    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * Gets the port number.
     *
     * @return the port number
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * Sets the port number.
     *
     * @param portNumber the new port number
     */
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * Gets the connection name.
     *
     * @return the connection name
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Sets the connection name.
     *
     * @param connectionName the new connection name
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
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
     * Gets the host name.
     *
     * @return the host name
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the host name.
     *
     * @param hostName the new host name
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Gets the service idorservice name.
     *
     * @return the service idorservice name
     */
    public String getServiceIdorserviceName() {
        return serviceIdorserviceName;
    }

    /**
     * Sets the service idorservice name.
     *
     * @param serviceIdorserviceName the new service idorservice name
     */
    public void setServiceIdorserviceName(String serviceIdorserviceName) {
        this.serviceIdorserviceName = serviceIdorserviceName;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the connection mode.
     *
     * @return the connection mode
     */
    public String getConnectionMode() {
        return connectionMode;
    }

    /**
     * Sets the connection mode.
     *
     * @param connectionMode the new connection mode
     */
    public void setConnectionMode(String connectionMode) {
        this.connectionMode = connectionMode;
    }

}
