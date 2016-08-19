package com.dataprocess.bods.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dataprocess.bods.handler.InstanceDetailsHandler;
import com.dataprocess.bods.web.form.InstanceDetailsForm;

public class InstanceDetailsAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String findForward = "forward.success";
		boolean hasCompleted = false;

		if (mapping.getPath().equals("/InstanceDetailsForm")) {
			InstanceDetailsForm instanceDetailsForm = (InstanceDetailsForm) form;

		} else if (mapping.getPath().equals("/InstanceDetailsAction")) {
			InstanceDetailsForm instanceDetailsForm = (InstanceDetailsForm) form;
			System.out.println("instance Name:"
					+ instanceDetailsForm.getInstanceDetailsVO()
							.getInstanceName());
			InstanceDetailsHandler instanceHandler = new InstanceDetailsHandler();

			hasCompleted = instanceHandler
					.saveInstanceDetails(instanceDetailsForm
							.getInstanceDetailsVO());
			if (hasCompleted) {
				System.out.println("Transaction Success");
			} else {
				System.out.println("Transaction Failed");
			}
		}

		return mapping.findForward(findForward);
	}
}
