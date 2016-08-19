package com.dataprocess.bods.web.form;

import org.apache.struts.action.ActionForm;

import com.dataprocess.bods.vo.InstanceDetailsVO;

public class InstanceDetailsForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7988972608963673307L;
	
	private InstanceDetailsVO instanceDetailsVO;
	
	public InstanceDetailsForm() {
		setInstanceDetailsVO(new InstanceDetailsVO());
	}
	
	public InstanceDetailsVO getInstanceDetailsVO() {
		return instanceDetailsVO;
	}

	public void setInstanceDetailsVO(InstanceDetailsVO instanceDetailsVO) {
		this.instanceDetailsVO = instanceDetailsVO;
	}
}
