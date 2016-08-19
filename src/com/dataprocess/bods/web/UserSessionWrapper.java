package com.dataprocess.bods.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dataprocess.bods.BODSConstants;

public final class UserSessionWrapper {

	private HttpSession session;
    private String employeeName = null;
    private String userName = null;
    private String emailId =null;
    
    private int employeeId = 0;
    private int userId = 0;
    
    public UserSessionWrapper(HttpSession paramSession) {
		session = paramSession;
	}
    
    public UserSessionWrapper(HttpServletRequest request) {
		session = request.getSession();
	}

	/*public Object getSessionObject(String attributeName) {
		return session.getAttribute(attributeName);
	}

	public void setSessionObject(String attributeName, Object sessionObject) {
		session.setAttribute(attributeName, sessionObject);
	}
    
	public String getEmployeeName() {
		employeeName = session.getAttribute("LoggedEmployeeName").toString();
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getCashRegName() {
		cashRegName = (String)session.getAttribute("CASHREGISTERNAME");
		return cashRegName;
	}

	public void setCashRegName(String cashRegName) {
		this.cashRegName = cashRegName;
	}

	public String getEmailId() {
		if(session.getAttribute(EChainConstants.LOGGED_USER_EMAIL) != null)
	    	emailId = session.getAttribute(EChainConstants.LOGGED_USER_EMAIL).toString();
	    else
	    	emailId = null;
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRetailWarehouse() {
		retailWarehouse =(String)session.getAttribute("RETAILWAREHOUSE");
		return retailWarehouse;
	}

	public void setRetailWarehouse(String retailWarehouse) {
		this.retailWarehouse = retailWarehouse;
	}

	public String getShiftName() {
		shiftName = (String)session.getAttribute("SHIFTNAME");
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public int getEmployeeId() {
		employeeId = ((Integer) session.getAttribute("LoggedEmployeeId")).intValue();
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getUserId() {
		userId = ((Integer) session.getAttribute("LoggedUserId")).intValue();
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCashRegId() {
		cashRegId = ((Integer)session.getAttribute("CASHREGISTERID")).intValue();
		return cashRegId;
	}

	public void setCashRegId(int cashRegId) {
		this.cashRegId = cashRegId;
	}

	public int getRetailWarehouseId() {
		retailWarehouseId = ((Integer)session.getAttribute("RETAILWAREHOUSEID")).intValue();
		return retailWarehouseId;
	}

	public void setRetailWarehouseId(int retailWarehouseId) {
		this.retailWarehouseId = retailWarehouseId;
	}

	public int getShiftId() {
		shiftId =((Integer)session.getAttribute("SHIFTID")).intValue();
		return shiftId;
	}

	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}

	public int getRegisterAssignmentId() {
		registerAssignmentId = ((Integer)session.getAttribute("REGISTERASSIGNMENTID")).intValue();
		return registerAssignmentId;
	}

	public void setRegisterAssignmentId(int registerAssignmentId) {
		this.registerAssignmentId = registerAssignmentId;
	}
	
	public void removeSessionObject(String attributeName) {
		session.removeAttribute(attributeName);
	}
	
	public void setMaxInactiveInterval(int integer) {
		session.setMaxInactiveInterval(integer);
	}

	*//**
	 * @return the session
	 *//*
	public HttpSession getSession() {
		return session;
	}

	*//**
	 * @param session the session to set
	 *//*
	public void setSession(HttpSession session) {
		this.session = session;
	}*/
    
    public String getUserName() {
		userName = session.getAttribute(BODSConstants.LOGGED_USER_NAME).toString();
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}

