package com.dataprocess.bods.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dataprocess.bods.web.form.ExtractForm;

public class ExtractAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExtractForm extractForm = (ExtractForm) form;
		
		try {
			
			if (mapping.getPath().equals("/ExtractionManualQueryAction")) {
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("forward.success");
	}
}
