package com.dataprocess.bods.web.form;

import org.apache.struts.action.ActionForm;

public class ExtractForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4951966245205034692L;

	private String instanceName;
	private String instanceId;
	private String instanceDescription;
	private String extractName;
	private String extractId;
	private String extractQueryDescription;
	
	public String getInstanceName() {
		return instanceName;
	}
	
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	
	public String getInstanceId() {
		return instanceId;
	}
	
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public String getInstanceDescription() {
		return instanceDescription;
	}
	
	public void setInstanceDescription(String instanceDescription) {
		this.instanceDescription = instanceDescription;
	}
	
	public String getExtractName() {
		return extractName;
	}
	
	public void setExtractName(String extractName) {
		this.extractName = extractName;
	}
	
	public String getExtractId() {
		return extractId;
	}
	
	public void setExtractId(String extractId) {
		this.extractId = extractId;
	}

	public String getExtractQueryDescription() {
		return extractQueryDescription;
	}

	public void setExtractQueryDescription(String extractQueryDescription) {
		this.extractQueryDescription = extractQueryDescription;
	}
}
