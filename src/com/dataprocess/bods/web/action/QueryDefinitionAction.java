package com.dataprocess.bods.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dataprocess.bods.handler.QueryDefinitionHandler;
import com.dataprocess.bods.vo.QueryDefinitionVO;
import com.dataprocess.bods.web.form.QueryDefinitionForm;

public class QueryDefinitionAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String forwardName = "";
		boolean hasCompleted = false;
		
		if (mapping.getPath().equals("/QueryDefinitionLaunch")) {
			QueryDefinitionForm queryDefinitionForm = (QueryDefinitionForm) form;
			QueryDefinitionHandler handler = new QueryDefinitionHandler();
			queryDefinitionForm.setQueryDefinitionVO(new QueryDefinitionVO());
			queryDefinitionForm.getQueryDefinitionVO().setSourceConfigConnectionList(handler.getSourceConfigConnectionList());
			forwardName = "forward.success";
			
		} else if (mapping.getPath().equals("/QueryDefinitionValidate")) {
			QueryDefinitionForm queryDefinitionForm = (QueryDefinitionForm) form;
			QueryDefinitionHandler queryDefinitionHandler = new QueryDefinitionHandler();
			hasCompleted = queryDefinitionHandler.validateQueryDefinition(queryDefinitionForm.getQueryDefinitionVO());
			
			if (hasCompleted) {
				queryDefinitionForm.getQueryDefinitionVO().setQueryValidatedFlag("Y");
				queryDefinitionForm.setQueryDefinitionVO(queryDefinitionForm.getQueryDefinitionVO());
				forwardName = "forward.success";
			} else {
				queryDefinitionForm.getQueryDefinitionVO().setQueryValidatedFlag("N");
				forwardName = "forward.failure";
			}
			
		} else if (mapping.getPath().equals("/QueryDefinitionColumnList")) {
			QueryDefinitionForm queryDefinitionForm = (QueryDefinitionForm) form;
			forwardName = "forward.success";
			
		} else if (mapping.getPath().equals("/QueryDefinitionDataList")) {
			QueryDefinitionForm queryDefinitionForm = (QueryDefinitionForm) form;
			forwardName = "forward.success";
			
		} else if (mapping.getPath().equals("/QueryDefinitionSaveAction")) {
			QueryDefinitionForm queryDefinitionForm = (QueryDefinitionForm) form;
			QueryDefinitionHandler queryDefinitionHandler = new QueryDefinitionHandler();
			hasCompleted = queryDefinitionHandler.saveQueryDefinition(queryDefinitionForm.getQueryDefinitionVO());
			forwardName = "forward.success";
			
		} else if (mapping.getPath().equals("/QueryDefinitionList")) {
			QueryDefinitionForm queryDefinitionForm = (QueryDefinitionForm) form;
			QueryDefinitionHandler queryDefinitionHandler = new QueryDefinitionHandler();
			queryDefinitionHandler.fetchQueryDefinitionList(queryDefinitionForm.getQueryDefinitionVO());
			forwardName = "forward.success";
			
		} else if (mapping.getPath().equals("/FetchQueryDefinitionDetails")) {
			QueryDefinitionForm queryDefinitionForm = (QueryDefinitionForm) form;
			QueryDefinitionVO queryDefinitionVO = null;
			QueryDefinitionHandler queryDefinitionHandler = new QueryDefinitionHandler();
			
			if (request.getParameter("sourceConfigNameId") != null ) {
				queryDefinitionForm.getQueryDefinitionVO().setSourceConfigNameId(Integer.parseInt(request.getParameter("sourceConfigNameId")));
			}
			queryDefinitionVO = queryDefinitionHandler.fetchQueryDefinitionDetails(queryDefinitionForm.getQueryDefinitionVO());
			queryDefinitionForm.setQueryDefinitionVO(queryDefinitionVO);
			queryDefinitionForm.getQueryDefinitionVO().setSourceConfigConnectionList(queryDefinitionHandler.getSourceConfigConnectionList());
			forwardName = "forward.success";
		}
		
		return mapping.findForward(forwardName);
	}
}
