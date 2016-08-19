package com.dataprocess.bods.vo;

import java.io.Serializable;

public class InstanceDetailsVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247224725094327526L;
	private String instanceName;
	private int instanceId;
	private String displayName;
	private String description;
	private String dbUserName;
	private String dbPassword;
	private String hostName;
	private String port;
	private String serviceName;
	private String instanceType;
	
	public String getInstanceName() {
		return instanceName;
	}
	
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	
	public int getInstanceId() {
		return instanceId;
	}
	
	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
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
	
	public String getDbUserName() {
		return dbUserName;
	}
	
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	
	public String getDbPassword() {
		return dbPassword;
	}
	
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getInstanceType() {
		return instanceType;
	}
	
	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}
}
