package com.dataprocess.bods.impl;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dataprocess.bods.dao.LoginDAO;
import com.dataprocess.bods.entity.LoginDetailEO;
import com.dataprocess.bods.util.connectionutil.HibernateSessionManager;
import com.dataprocess.bods.vo.LoginVO;

public class LoginDAOImpl implements LoginDAO {

	public boolean checkLoginDetails(LoginVO loginVo) {
		StringBuffer queryBuffer = null;
		boolean hasCompleted = false;
		Session session = null;
		SQLQuery sqlQuery = null;
		Transaction transaction = null;
		try {
			session = HibernateSessionManager.getHibernateSession();
			transaction = session.beginTransaction();
			queryBuffer = new StringBuffer();
			queryBuffer.append(" SELECT ");
			queryBuffer.append(" ETL_LOGIN_ID ");
			queryBuffer.append(" FROM ");
			queryBuffer.append(" ETL_LOGIN_USERS ");
			queryBuffer.append(" WHERE ");
			queryBuffer.append(" USERNAME = ? ");
			queryBuffer.append(" AND PASSWORD = ? ");
			
			sqlQuery = session.createSQLQuery(queryBuffer.toString());
			sqlQuery.setParameter(0, loginVo.getUserName());
			sqlQuery.setParameter(1, loginVo.getPassword());
			int count = sqlQuery.executeUpdate();
			
			if (count > 0) {
				//loginDetailEO.setLoginFlag("Y");
				hasCompleted = true;
			} else {
				//loginDetailEO.setLoginFlag("N");
			}
			
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			
		} finally {
			
			if (session != null) {
				session.close();
			}
		}
		return hasCompleted;
	}

}
