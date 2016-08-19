package com.dataprocess.bods.handler;

import com.dataprocess.bods.business.LoginBusiness;
import com.dataprocess.bods.vo.LoginVO;

public class LoginHandler {

	public boolean checkLoginDetails(LoginVO loginVo) {
		LoginBusiness business = null;
		boolean hasCompleted = false;
		
		try {
			business = new LoginBusiness();
			hasCompleted = business.checkLoginDetails(loginVo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasCompleted;
	}
}
