package com.dataprocess.bods.web.form;

import org.apache.struts.action.ActionForm;

import com.dataprocess.bods.vo.LoginVO;

public class LoginForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8289902481752418265L;

	private LoginVO loginVO;
	private String loginFlag;
	
	public LoginForm() {
		setLoginVO(new LoginVO());
	}
	
	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public LoginVO getLoginVO() {
		return loginVO;
	}

	public void setLoginVO(LoginVO loginVO) {
		this.loginVO = loginVO;
	}
}