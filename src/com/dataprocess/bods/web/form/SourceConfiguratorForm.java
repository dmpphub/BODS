package com.dataprocess.bods.web.form;

import org.apache.struts.action.ActionForm;

import com.dataprocess.bods.vo.SourceConfiguratorVO;

public class SourceConfiguratorForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7636635830359114418L;
	
	private SourceConfiguratorVO sourceConfiguratorVO;

	public SourceConfiguratorForm() {
		setSourceConfiguratorVO(new SourceConfiguratorVO());
	}

	public SourceConfiguratorVO getSourceConfiguratorVO() {
		return sourceConfiguratorVO;
	}

	public void setSourceConfiguratorVO(SourceConfiguratorVO sourceConfiguratorVO) {
		this.sourceConfiguratorVO = sourceConfiguratorVO;
	}
}
