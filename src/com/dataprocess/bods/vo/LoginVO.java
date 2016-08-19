package com.dataprocess.bods.vo;

import java.io.Serializable;

import com.dataprocess.bods.util.EntityProperty;

public class LoginVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9170605182973292755L;

	@EntityProperty(columnName="sourceConfiguratorId")
	private int etlLoginId;
	private String userName;
	private String password;
	private String loginFlag;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public int getEtlLoginId() {
		return etlLoginId;
	}

	public void setEtlLoginId(int etlLoginId) {
		this.etlLoginId = etlLoginId;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}
}
