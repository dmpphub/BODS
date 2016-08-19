package com.dataprocess.bods.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dataprocess.bods.BODSConstants;
import com.dataprocess.bods.handler.LoginHandler;
import com.dataprocess.bods.vo.LoginVO;
import com.dataprocess.bods.web.form.LoginForm;
import com.dataprocess.bods.web.obj.LoginActionObject;

public class LoginAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm loginForm = (LoginForm) form;
		HttpSession session = null;
		String findForward = "login.success";
		
		try {
			
			if (mapping.getPath().equals("/LoginForm")) {
				
			} else if (mapping.getPath().equals("/LoginAction")) {
				boolean hasProcessed = false;
				LoginActionObject actionObj = new LoginActionObject();
				LoginHandler handler = new LoginHandler();
				LoginVO loginVo = new LoginVO();
				actionObj.fillFormToBean(request, response, loginForm, loginVo);
				hasProcessed = handler.checkLoginDetails(loginForm.getLoginVO());
				actionObj.fillBeanToForm(request, response, loginForm, loginVo);
				
				if (hasProcessed) {
					session = request.getSession();
					session.setAttribute(BODSConstants.LOGGED_USER_NAME, loginForm.getLoginVO().getUserName());
					findForward = "forward.success";
					
				} else {
					findForward = "login.failure";
				}
				
			} else if (mapping.getPath().equals("/home")) {
				findForward = "forward.success";
				
			} else if (mapping.getPath().equals("/Logout")) {
				session = request.getSession();
				session.removeAttribute(BODSConstants.LOGGED_USER_NAME);
				findForward = "logout.success";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward(findForward);
	}

}
