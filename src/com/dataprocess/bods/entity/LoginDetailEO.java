package com.dataprocess.bods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ETL_LOGIN_USERS")
public class LoginDetailEO {
	
	@Id
	@Column(name = "ETL_LOGIN_ID")
	private int etlLoginId;
	
	@Column(name = "USERNAME")
	private String userName;
	
	@Column(name = "PASSWORD")
	private String password;

	public int getEtlLoginId() {
		return etlLoginId;
	}

	public void setEtlLoginId(int etlLoginId) {
		this.etlLoginId = etlLoginId;
	}

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
}
