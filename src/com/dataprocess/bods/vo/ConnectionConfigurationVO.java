package com.dataprocess.bods.vo;

import java.io.Serializable;

/**
 * The Class ConnectionConfigurationVO.
 */
public class ConnectionConfigurationVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2017180047903138828L;

	/** The connection id. */
	private int connectionId;

	/** The port number. */
	private int portNumber;

	/** The connection name. */
	private String connectionName;

	/** The display name. */
	private String displayName;

	/** The description. */
	private String description;

	/** The database type. */
	private String databaseType;

	/** The host name. */
	private String hostName;

	/** The service id. */
	private String serviceId;

	/** The service name. */
	private String serviceName;

	/** The user name. */
	private String userName;

	/** The password. */
	private String password;

	/** The connection mode. */
	private String connectionMode;

	/** The bidirectional connection mode. */
	private String bidirectionalConnectionMode;

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
	 * Gets the database type.
	 *
	 * @return the database type
	 */
	public String getDatabaseType() {
		return databaseType;
	}

	/**
	 * Sets the database type.
	 *
	 * @param databaseType the new database type
	 */
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
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
	 * Gets the service id.
	 *
	 * @return the service id
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * Sets the service id.
	 *
	 * @param serviceId the new service id
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Sets the service name.
	 *
	 * @param serviceName the new service name
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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

	/**
	 * Gets the bidirectional connection mode.
	 *
	 * @return the bidirectional connection mode
	 */
	public String getBidirectionalConnectionMode() {
		return bidirectionalConnectionMode;
	}

	/**
	 * Sets the bidirectional connection mode.
	 *
	 * @param bidirectionalConnectionMode the new bidirectional connection mode
	 */
	public void setBidirectionalConnectionMode(
			String bidirectionalConnectionMode) {
		this.bidirectionalConnectionMode = bidirectionalConnectionMode;
	}
}
