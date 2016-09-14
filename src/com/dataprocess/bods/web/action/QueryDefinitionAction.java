package com.dataprocess.bods.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dataprocess.bods.handler.QueryDefinitionHandler;
import com.dataprocess.bods.util.TransactionMessage;
import com.dataprocess.bods.vo.QueryDefinitionVO;
import com.dataprocess.bods.web.form.QueryDefinitionForm;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryDefinitionAction.
 */
public class QueryDefinitionAction extends Action {

    /*
     * (non-Javadoc)
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
     * org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception {

        boolean hasCompleted = false;
        String forwardName = "forward.success";
        QueryDefinitionVO queryDefinitionVO = null;
        QueryDefinitionForm queryDefinitionForm = (QueryDefinitionForm) form;
        QueryDefinitionHandler queryDefinitionHandler = null;

        if (mapping.getPath().equals("/QueryDefinitionLaunch")) {
            queryDefinitionHandler = new QueryDefinitionHandler();
            queryDefinitionForm.setQueryDefinitionVO(new QueryDefinitionVO());
            queryDefinitionForm.getQueryDefinitionVO().setSourceConfigConnectionList(
                queryDefinitionHandler.getSourceConfigConnectionList());
        } else if (mapping.getPath().equals("/QueryDefinitionValidate")) {
            queryDefinitionHandler = new QueryDefinitionHandler();
            hasCompleted = queryDefinitionHandler.validateQueryDefinition(queryDefinitionForm.getQueryDefinitionVO());
            if (hasCompleted) {
                queryDefinitionForm.getQueryDefinitionVO().setQueryValidatedFlag("Y");
                queryDefinitionForm.setQueryDefinitionVO(queryDefinitionForm.getQueryDefinitionVO());
                request.setAttribute("transactionSuccessMessage", new TransactionMessage("Transaction Successful!!!!", ""));
            } else {
                queryDefinitionForm.getQueryDefinitionVO().setQueryValidatedFlag("N");
                request.setAttribute("transactionSuccessMessage", new TransactionMessage("Transaction Failure!!!!", ""));
                forwardName = "forward.failure";
            }
        } else if (mapping.getPath().equals("/QueryDefinitionColumnList")) {

        } else if (mapping.getPath().equals("/QueryDefinitionDataList")) {
            queryDefinitionHandler = new QueryDefinitionHandler();
            queryDefinitionVO = queryDefinitionForm.getQueryDefinitionVO();
            queryDefinitionHandler.showDataTableGrid(queryDefinitionVO);
            queryDefinitionForm.setQueryDefinitionVO(queryDefinitionVO);
        } else if (mapping.getPath().equals("/QueryDefinitionSaveAction")) {
            queryDefinitionHandler = new QueryDefinitionHandler();
            queryDefinitionVO = queryDefinitionHandler.saveQueryDefinition(queryDefinitionForm.getQueryDefinitionVO());
            queryDefinitionForm.setQueryDefinitionVO(queryDefinitionVO);
        } else if (mapping.getPath().equals("/QueryDefinitionList")) {
            queryDefinitionHandler = new QueryDefinitionHandler();
            queryDefinitionHandler.fetchQueryDefinitionList(queryDefinitionForm.getQueryDefinitionVO());
        } else if (mapping.getPath().equals("/FetchQueryDefinitionDetails")) {
            queryDefinitionHandler = new QueryDefinitionHandler();
            if (request.getParameter("sourceConfiguratorId") != null) {
                queryDefinitionForm.getQueryDefinitionVO().setSourceConfiguratorId(
                    Integer.parseInt(request.getParameter("sourceConfiguratorId")));
            }
            queryDefinitionVO =
                queryDefinitionHandler.fetchQueryDefinitionDetails(queryDefinitionForm.getQueryDefinitionVO());
            queryDefinitionForm.setQueryDefinitionVO(queryDefinitionVO);
            queryDefinitionForm.getQueryDefinitionVO().setSourceConfigConnectionList(
                queryDefinitionHandler.getSourceConfigConnectionList());
        }

        return mapping.findForward(forwardName);
    }
}
