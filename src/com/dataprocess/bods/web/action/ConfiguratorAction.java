package com.dataprocess.bods.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.dataprocess.bods.handler.ConfiguratorHandler;
import com.dataprocess.bods.vo.ConfiguratorVO;
import com.dataprocess.bods.vo.ConfiguratorValidationVO;
import com.dataprocess.bods.web.form.ConfiguratorForm;

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
        String stagingTable = "";
        String pvTableName = "";
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

            if (request.getParameter("name") != null) {
                for (int i = 0; i < configuratorForm.getConfiguratorVO().getConfiguratorValidationVOList().size(); i++) {
                    configuratorValidationVO =
                        configuratorForm.getConfiguratorVO().getConfiguratorValidationVOList().get(i);
                    if (configuratorValidationVO.equals(request.getParameter("name"))) {
                        configuratorForm.getConfiguratorVO().setConfiguratorValidationVO(configuratorValidationVO);
                    }
                }
            } else {
                configuratorValidationVO.setActive("Y");
                configuratorValidationVO.setValidationInference("Error");
                configuratorForm.getConfiguratorVO().setConfiguratorValidationVO(configuratorValidationVO);
            }
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
            configuratorForm.setConfiguratorVO(configuratorHandler.execute(configuratorId, configuratorConnId));
            forwardName = "forward.success.processsummary";
        } else if (mapping.getPath().equals("/PrevalidationProcessLaunchMapping")) {
            configuratorVO = configuratorForm.getConfiguratorVO();
            stagingTable =
                "STG_" + configuratorVO.getConfiguratorConnectionId() + "_" + configuratorVO.getConfiguratorId();
            String dataTemplateName = configuratorVO.getConfiguratorName();
            configuratorHandler = new ConfiguratorHandler();
            configuratorVO.setPrevalDataString(configuratorHandler
                .getStagintTableDetail(stagingTable, dataTemplateName));
        } else if (mapping.getPath().equals("/PrevalidationProcessFetchMapping")) {
            configuratorVO = configuratorForm.getConfiguratorVO();
            pvTableName =
                "PV_" + configuratorVO.getConfiguratorConnectionId() + "_" + configuratorVO.getConfiguratorId();
            configuratorHandler = new ConfiguratorHandler();
            convertedJson = configuratorHandler.getPrevalidationDetail(pvTableName);
            valueObject.put("convertedJson", convertedJson);
            printWriter = response.getWriter();
            printWriter.print(valueObject.get("convertedJson").toString());
            printWriter.flush();
            printWriter.close();
            forwardName = null;
        } else if (mapping.getPath().equals("/ConfiguratorFinalExecuteMapping")) {
            configuratorHandler = new ConfiguratorHandler();
            configuratorVO = configuratorForm.getConfiguratorVO();
            configuratorHandler.executeStagingProcedure(configuratorVO);
            forwardName = "forward.success.processsummary";
        } else if (mapping.getPath().equals("/ConfiguratorList")) {
            configuratorHandler = new ConfiguratorHandler();
            configuratorVO = configuratorHandler.fetchConfiguratorList(configuratorForm.getConfiguratorVO());
            configuratorForm.setConfiguratorVO(configuratorVO);
            forwardName = "forward.success";
        } else if (mapping.getPath().equals("/FetchConfiguratorDetails")) {
            configuratorHandler = new ConfiguratorHandler();
            if (request.getParameter("configuratorId") != null) {
                configuratorForm.getConfiguratorVO().setConfiguratorId(
                    Integer.parseInt(request.getParameter("configuratorId")));
            }
            configuratorVO = configuratorHandler.fetchConfigurationDetails(configuratorForm.getConfiguratorVO());
            configuratorVO.setConfiguratorId(Integer.parseInt(request.getParameter("configuratorId")));
            configuratorForm.setConfiguratorVO(configuratorVO);
            forwardName = "forward.success";
        } else if (mapping.getPath().equals("/ValidationList")) {
            configuratorHandler = new ConfiguratorHandler();
            List<ConfiguratorValidationVO> validationVOList = null;
            validationVOList = configuratorForm.getConfiguratorVO().getConfiguratorValidationVOList();
            if (validationVOList != null && validationVOList.size() > 0) {
                forwardName = "forward.success";
            } else {
                forwardName = "forward.failure";
            }
        } else if (mapping.getPath().equals("/TargetColumnMappingLaunch")) {
            System.out.println(configuratorForm.getConfiguratorVO().getConfiguratorInterfaceColumnVOList());
        } else if (mapping.getPath().equals("/CheckingConfiguratorRunningStautsMapping")) {
            configuratorHandler = new ConfiguratorHandler();
            String currentStatus = "";
            int configuratorExecId = Integer.parseInt(request.getParameter("configuratorExecId"));
            currentStatus = configuratorHandler.getConfiguratorCurrentStatus(configuratorExecId);
            printWriter = response.getWriter();
            printWriter.print(currentStatus);
            printWriter.flush();
            printWriter.close();
            forwardName = null;
        } else if (mapping.getPath().equals("/CompletedRecordDisplayMapping")) {
            configuratorHandler = new ConfiguratorHandler();
            String currentStatus = "";
            configuratorVO = configuratorForm.getConfiguratorVO();
            String stagingTableName =
                "STG_" + configuratorVO.getConfiguratorConnectionId() + "_" + configuratorVO.getConfiguratorId();
            currentStatus = configuratorHandler.getCompletedRecordCountDetails(stagingTableName, configuratorVO);
            printWriter = response.getWriter();
            printWriter.print(currentStatus);
            printWriter.flush();
            printWriter.close();
            forwardName = null;
        } else if (mapping.getPath().equals("/DisplayRecordFileMapping")) {
            String type = request.getParameter("type");
            String reportPath = "";
            configuratorVO = configuratorForm.getConfiguratorVO();
            if ("Success".equals(type)) {
                reportPath = "D:\\fileOutput\\" + configuratorVO.getConfiguratorName() + "_SUCCESS.xlsx";
            } else {
                reportPath = "D:\\fileOutput\\" + configuratorVO.getConfiguratorName() + "_ERROR.xlsx";
            }
            File file = new File(reportPath);
            FileInputStream xlsStream = new FileInputStream(file);
            response.setContentType("application/vnd.openxml");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "attachment;filename=\"" + file + "\"");
            response.setContentLength((int) file.length());
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(xlsStream, outputStream);
            outputStream.flush();
            outputStream.close();
            xlsStream.close();
        }
        return mapping.findForward(forwardName);
    }
}
