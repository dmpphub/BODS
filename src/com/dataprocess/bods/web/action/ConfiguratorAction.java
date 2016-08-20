package com.dataprocess.bods.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.dataprocess.bods.handler.ConfiguratorHandler;
import com.dataprocess.bods.vo.ConfiguratorVO;
import com.dataprocess.bods.vo.ConfiguratorValidationVO;
import com.dataprocess.bods.web.form.ConfiguratorForm;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfiguratorAction.
 */
public final class ConfiguratorAction extends Action {

    /*
     * (non-Javadoc)
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action. ActionMapping,
     * org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        JSONObject convertedJson = null;
        JSONObject valueObject = new JSONObject();
        String forwardName = "forward.success";
        ConfiguratorForm configuratorForm = (ConfiguratorForm) form;
        ConfiguratorHandler configuratorHandler = null;
        ConfiguratorVO configuratorVO = null;
        ConfiguratorValidationVO configuratorValidationVO = null;
        List<ConfiguratorValidationVO> configuratorValidationVOList = null;
        PrintWriter printWriter = null;

        if (mapping.getPath().equals("/ConfiguratorLaunch")) {
            configuratorHandler = new ConfiguratorHandler();
            configuratorForm.setConfiguratorVO(new ConfiguratorVO());
            configuratorForm.getConfiguratorVO().setConfigConnectionList(configuratorHandler.getConfigConnectionList());
        } else if (mapping.getPath().equals("/ConfiguratorColumnDefinition")) {
            configuratorHandler = new ConfiguratorHandler();
            configuratorVO = configuratorForm.getConfiguratorVO();
            configuratorForm.getConfiguratorVO().setSourceConfigConnectionList(
                configuratorHandler.getSourceConfigConnectionList());
            configuratorForm.getConfiguratorVO().setSourceConfigurationNameList(
                configuratorHandler.getSourceConfigurationNameList());
            if (configuratorVO.getSourceConfigurationId() > 0) {
                configuratorHandler.getSourceConfiguratorColumn(configuratorVO);
                configuratorForm.setConfiguratorVO(configuratorVO);
            }
        } else if (mapping.getPath().equals("/GetTargetTableColumnMapping")) {
            configuratorHandler = new ConfiguratorHandler();
            String tableName = request.getParameter("tableName").toString();
            configuratorVO = configuratorForm.getConfiguratorVO();
            configuratorVO.getConfiguratorInterfaceColumnVO().setTableName(tableName);
            convertedJson = configuratorHandler.getInterfaceTableColumnInfo(configuratorVO);
            valueObject.put("convertedJson", convertedJson);
            printWriter = response.getWriter();
            printWriter.print(valueObject.get("convertedJson").toString());
            printWriter.flush();
            printWriter.close();
            forwardName = null;
        } else if (mapping.getPath().equals("/InterfaceColumnMapping")) {

        } else if (mapping.getPath().equals("/ConfiguratorSaveMapping")) {
            configuratorHandler = new ConfiguratorHandler();
            configuratorVO = configuratorForm.getConfiguratorVO();
            configuratorHandler.saveConfiguratorDetails(configuratorVO);
        } else if (mapping.getPath().equals("/ValidationLaunch")) {
            configuratorHandler = new ConfiguratorHandler();
            configuratorValidationVO = new ConfiguratorValidationVO();
            configuratorValidationVO.setActive("Y");
            configuratorValidationVO.setValidationInference("Error");
            configuratorForm.getConfiguratorVO().setConfiguratorValidationVO(configuratorValidationVO);
        } else if (mapping.getPath().equals("/QueryValidationMapping")) {
            String query = request.getParameter("query").toString();
            configuratorHandler = new ConfiguratorHandler();
            configuratorVO = configuratorForm.getConfiguratorVO();
            configuratorVO.getConfiguratorValidationVO().setValidationQuery(query);
            convertedJson = configuratorHandler.parseQuery(configuratorVO);
            valueObject.put("convertedJson", convertedJson);
            printWriter = response.getWriter();
            printWriter.print(valueObject.get("convertedJson").toString());
            printWriter.flush();
            printWriter.close();
            forwardName = null;
        } else if (mapping.getPath().equals("/ValidationSaveMapping")) {
            configuratorValidationVOList = new ArrayList<ConfiguratorValidationVO>();
            configuratorVO = configuratorForm.getConfiguratorVO();
            if (configuratorVO.getConfiguratorValidationVOList() != null
                && configuratorVO.getConfiguratorValidationVOList().size() > 0) {
                configuratorValidationVOList = configuratorVO.getConfiguratorValidationVOList();
                configuratorValidationVOList.add(configuratorVO.getConfiguratorValidationVO());
            } else {
                configuratorValidationVOList.add(configuratorVO.getConfiguratorValidationVO());
            }
            configuratorVO.setConfiguratorValidationVOList(configuratorValidationVOList);
            configuratorForm.setConfiguratorVO(configuratorVO);
        } else if (mapping.getPath().equals("/ConfiguratorSaveAction")) {
            configuratorHandler = new ConfiguratorHandler();
            configuratorVO = configuratorForm.getConfiguratorVO();
            configuratorHandler.saveConfiguratorDetails(configuratorVO);
        } else if (mapping.getPath().equals("/ConfiguratorExecuteMapping")) {
            int configuratorConnId = Integer.parseInt(request.getParameter("configuratorConnId"));
            int configuratorId = Integer.parseInt(request.getParameter("configuratorId"));
            configuratorHandler = new ConfiguratorHandler();
            configuratorHandler.execute(configuratorId, configuratorConnId);
        } else if (mapping.getPath().equals("/PrevalidationProcessLaunch")) {

        }
        return mapping.findForward(forwardName);
    }
}
