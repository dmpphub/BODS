package com.dataprocess.bods.business;

import com.dataprocess.bods.entity.LoginDetailEO;
import com.dataprocess.bods.impl.LoginDAOImpl;
import com.dataprocess.bods.util.BeanUtils;
import com.dataprocess.bods.vo.LoginVO;

public class LoginBusiness {

	public boolean checkLoginDetails(LoginVO loginVo) {
		LoginDAOImpl loginDao = null;
		BeanUtils beanUtils = new BeanUtils();
		LoginDetailEO loginDetailEO = new LoginDetailEO();
		boolean hasCompleted = false;
		
		try {
			loginDao = new LoginDAOImpl();
			//beanUtils.populateToEntityObject(loginVo, loginDetailEO);
			hasCompleted = loginDao.checkLoginDetails(loginVo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasCompleted;
	}

}
