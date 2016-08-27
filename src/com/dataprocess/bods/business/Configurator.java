package com.dataprocess.bods.business;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONObject;

import com.dataprocess.bods.dao.ConfiguratorDAO;
import com.dataprocess.bods.dao.ConfiguratorValidationDAO;
import com.dataprocess.bods.entity.ConfiguratorBinariesEO;
import com.dataprocess.bods.entity.ConfiguratorEO;
import com.dataprocess.bods.entity.QueryDefinitionEO;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.util.BytesUtil;
import com.dataprocess.bods.util.ImageUtils;
import com.dataprocess.bods.util.SpringBeanUtils;
import com.dataprocess.bods.util.connectionutil.TargetSchemaConnection;
import com.dataprocess.bods.vo.ConfiguratorColumnDefinitionVO;
import com.dataprocess.bods.vo.ConfiguratorInterfaceColumnVO;
import com.dataprocess.bods.vo.ConfiguratorVO;
import com.dataprocess.bods.vo.PrevalidationStatusVO;
import com.dataprocess.bods.vo.QueryDefinitionLineVO;
import com.dataprocess.bods.vo.QueryDefinitionVO;

// TODO: Auto-generated Javadoc
/**
 * The Class Configurator.
 */
public final class Configurator {

    /**
     * Gets the source configurator column.
     * 
     * @param sourceConfiguratorId the source configurator id
     * @return the source configurator column
     * @throws BODSException the bODS exception
     */
    public List<ConfiguratorColumnDefinitionVO> getSourceConfiguratorColumn(int sourceConfiguratorId)
        throws BODSException {
        QueryDefinitionVO queryDefinitionVO = null;
        List<ConfiguratorColumnDefinitionVO> configuratorColDefnList = null;
        QueryDefinitionEO queryDefinitionEO = null;
        ConfiguratorDAO configuratorDAO = null;
        try {
            queryDefinitionVO = new QueryDefinitionVO();
            configuratorDAO = new ConfiguratorDAO();
            queryDefinitionEO = configuratorDAO.getSourceConfigurator(sourceConfiguratorId);
            queryDefinitionVO =
                (QueryDefinitionVO) new SpringBeanUtils().populateToDTOObject(queryDefinitionEO, queryDefinitionVO);
            configuratorColDefnList =
                fillConfiguratorColumnDefinitionVOList(queryDefinitionVO.getSourceConfiguratorLineVOSet());

        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("Configurator", "getSourceConfiguratorColumn", exception.getMessage());
        }
        return configuratorColDefnList;
    }

    /**
     * Fill configurator column definition vo list.
     *
     * @param queryDefinitionLineVO the query definition line vo
     * @return the list
     * @throws BODSException the bODS exception
     */
    private List<ConfiguratorColumnDefinitionVO> fillConfiguratorColumnDefinitionVOList(
        Set<QueryDefinitionLineVO> queryDefinitionLineVO) throws BODSException {
        ConfiguratorColumnDefinitionVO configuratorColumnDefinitionVO = null;
        List<ConfiguratorColumnDefinitionVO> configuratorColDefnList = null;
        try {
            configuratorColDefnList = new ArrayList<>();
            for (QueryDefinitionLineVO sourceConfiguratorLineVO1 : queryDefinitionLineVO) {
                configuratorColumnDefinitionVO = new ConfiguratorColumnDefinitionVO();
                configuratorColumnDefinitionVO.setColumnName(sourceConfiguratorLineVO1.getColumnName());
                configuratorColumnDefinitionVO.setColumnId(sourceConfiguratorLineVO1.getSourceConfiguratorLineId());
                configuratorColumnDefinitionVO.setDataType(sourceConfiguratorLineVO1.getDataType());
                configuratorColumnDefinitionVO.setConversionColumn(sourceConfiguratorLineVO1.getConversionColumn());
                configuratorColumnDefinitionVO.setMandatory(sourceConfiguratorLineVO1.getColumnMandatoryFlag());
                configuratorColumnDefinitionVO.setUnique(sourceConfiguratorLineVO1.getColumnUniqueFlag());
                configuratorColDefnList.add(configuratorColumnDefinitionVO);
            }
        } catch (Exception exception) {
            throw new BODSException("Configurator", "fillConfiguratorColumnDefinitionVOList", exception.getMessage());
        }
        return configuratorColDefnList;

    }

    /**
     * Gets the interface table column list.
     * 
     * @param configuratorVO the configurator vo
     * @return the interface table column list
     * @throws BODSException the bODS exception
     */
    public List<ConfiguratorInterfaceColumnVO> getInterfaceTableColumnList(ConfiguratorVO configuratorVO)
        throws BODSException {
        String tableName = "";
        ConfiguratorDAO configuratorDAO = null;
        List<ConfiguratorInterfaceColumnVO> configuratorInterfaceColumnVOList = null;
        try {
            configuratorDAO = new ConfiguratorDAO();
            tableName = configuratorVO.getConfiguratorInterfaceColumnVO().getTableName();
            configuratorInterfaceColumnVOList =
                configuratorDAO.getInterfaceTableColumnList(configuratorVO.getConfiguratorConnectionId(), tableName);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("Configurator", "getInterfaceTableColumnList", exception.getMessage());
        }
        return configuratorInterfaceColumnVOList;
    }

    /**
     * Creates the staging table.
     * 
     * @param configuratorVO the configurator vo
     * @throws BODSException the bODS exception
     */
    public void createConfiguratorDynamicTable(ConfiguratorVO configuratorVO) throws BODSException {
        String stagingTableScript = "";
        String tableName = "";
        String pvtableName = "";
        String pvColumn = "";
        Connection connection = null;
        ConfiguratorDAO configuratorDAO = null;
        TargetSchemaConnection targetSchemaConnection = null;
        try {
            configuratorDAO = new ConfiguratorDAO();
            targetSchemaConnection = new TargetSchemaConnection();
            connection = targetSchemaConnection.getTargetSchemaConnection(configuratorVO.getConfiguratorConnectionId());
            tableName =
                "STG_" + configuratorVO.getConfiguratorConnectionId() + "_" + configuratorVO.getConfiguratorId();
            pvtableName =
                "PV_" + configuratorVO.getConfiguratorConnectionId() + "_" + configuratorVO.getConfiguratorId();
            stagingTableScript += generatePVTableScripts(pvtableName);
            configuratorDAO.createPrevalidationTable(connection, stagingTableScript, pvtableName);
            pvColumn = configuratorDAO.insertPrevalidationValues(connection, pvtableName, configuratorVO);
            stagingTableScript = "";
            stagingTableScript = "CREATE TABLE " + tableName;
            stagingTableScript += getColumnList(configuratorVO.getConfiguratorColumnDefinitionVOList());
            if (pvColumn != null && !pvColumn.equals("")) {
                stagingTableScript += pvColumn;
            }
            configuratorDAO.createStagingTable(connection, stagingTableScript, tableName);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("Configurator", "createConfiguratorDynamicTable", exception.getMessage());
        } finally {
            targetSchemaConnection.targetSchemaClose(connection, null, null, null);
        }
    }

    /**
     * Generate pv table scripts.
     *
     * @param pvStagingTable the pv staging table
     * @return the string
     * @throws BODSException the bODS exception
     */
    public String generatePVTableScripts(String pvStagingTable) throws BODSException {
        StringBuffer pvTableScripts = null;
        try {
            pvTableScripts = new StringBuffer();
            pvTableScripts.append(" CREATE TABLE " + pvStagingTable + "( ");
            pvTableScripts.append(" PV_SEQ_ID NUMBER,");
            pvTableScripts.append(" EXECUTION_ID NUMBER,");
            pvTableScripts.append(" CONFIGURATOR_ID NUMBER,");
            pvTableScripts.append(" PV_SEQ_ORDER_NO VARCHAR2(100),");
            pvTableScripts.append(" PV_TYPE VARCHAR2(1),");
            pvTableScripts.append(" PV_ATTRIBUTE VARCHAR2(100),");
            pvTableScripts.append(" SUCCESS_MESSAGE VARCHAR2(500),");
            pvTableScripts.append(" ERROR_MESSAGE VARCHAR2(500),");
            pvTableScripts.append(" SUCCESS_COUNT NUMBER,");
            pvTableScripts.append(" ERROR_COUNT NUMBER,");
            pvTableScripts.append(" START_TIME TIMESTAMP,");
            pvTableScripts.append(" END_TIME  TIMESTAMP )");
        } catch (Exception exception) {
            throw new BODSException("Configurator", "generatePVTableScripts", exception.getMessage());
        }
        return pvTableScripts.toString();
    }

    /**
     * Gets the column list.
     * 
     * @param configuratorColumnDefinitionVOList the configurator column definition vo list
     * @return the column list
     * @throws BODSException the bODS exception
     */
    private String getColumnList(List<ConfiguratorColumnDefinitionVO> configuratorColumnDefinitionVOList)
        throws BODSException {
        int index = 0;
        String columnName = "";
        String finalColumnName = "";
        String commonColumnName = "";
        try {
            for (ConfiguratorColumnDefinitionVO configuratorColumnDefinitionVO : configuratorColumnDefinitionVOList) {
                if (index == 0) {
                    if ("String".equalsIgnoreCase(configuratorColumnDefinitionVO.getDataType())) {
                        columnName = configuratorColumnDefinitionVO.getColumnName() + " VARCHAR2(2000) ";
                    } else {
                        columnName =
                            configuratorColumnDefinitionVO.getColumnName() + " "
                                + configuratorColumnDefinitionVO.getDataType();
                    }
                } else {
                    if ("String".equalsIgnoreCase(configuratorColumnDefinitionVO.getDataType())) {
                        columnName += ", " + configuratorColumnDefinitionVO.getColumnName() + " VARCHAR2(2000) ";
                    } else {
                        columnName +=
                            ", " + configuratorColumnDefinitionVO.getColumnName() + " "
                                + configuratorColumnDefinitionVO.getDataType();
                    }
                }
                index++;
            }
            commonColumnName =
                " , LINE_NO NUMBER, EXECUTION_ID NUMBER, CONFIGURATOR_ID NUMBER, STATUS_CODE VARCHAR2(3), DC_FLAG VARCHAR2(3)";
            columnName += commonColumnName;
            finalColumnName = " ( " + columnName + " ) ";
        } catch (Exception exception) {
            throw new BODSException("Configurator", "getInterfaceTableColumnList", exception.getMessage());
        }
        return finalColumnName;
    }

    /**
     * Parses the query.
     * 
     * @param configuratorVO the configurator vo
     * @return the string
     * @throws BODSException the bODS exception
     */
    public String parseQuery(ConfiguratorVO configuratorVO) throws BODSException {
        String prevalidationQuery = "";
        String prevalidationType = "";
        String jsonString = "";
        JSONObject valueObject = null;
        JSONObject oracleValidationVOJSON = null;
        Connection connection = null;
        TargetSchemaConnection targetSchemaConnection = null;
        ConfiguratorValidationDAO configuratorValidationDAO = null;
        List<String> referenceColumnNameList = null;
        try {
            valueObject = new JSONObject();
            configuratorValidationDAO = new ConfiguratorValidationDAO();
            targetSchemaConnection = new TargetSchemaConnection();
            connection = targetSchemaConnection.getTargetSchemaConnection(configuratorVO.getConfiguratorConnectionId());
            prevalidationType = "Query";
            prevalidationQuery = configuratorVO.getConfiguratorValidationVO().getValidationQuery();
            referenceColumnNameList =
                configuratorValidationDAO.parseQuery(connection, prevalidationType, prevalidationQuery);
            valueObject.append("queryColumnNameList", referenceColumnNameList);
            valueObject.append("configuratorColumnVOList", configuratorVO.getConfiguratorColumnDefinitionVOList());
            oracleValidationVOJSON = new JSONObject();
            oracleValidationVOJSON.put("configuratorVO", valueObject);
            jsonString = oracleValidationVOJSON.toString();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("Configurator", "parseQuery", exception.getMessage());
        }
        return jsonString;
    }

    /**
     * Gets the config connection list.
     *
     * @return the config connection list
     * @throws BODSException the bODS exception
     */
    public ArrayList<ConfiguratorVO> getConfigConnectionList() throws BODSException {
        ConfiguratorDAO configuratorDAO = null;
        ArrayList<ConfiguratorVO> configConnectionList = null;
        try {
            configuratorDAO = new ConfiguratorDAO();
            configConnectionList = configuratorDAO.getConfigConnectionList();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("Configurator", "getConfigConnectionList", exception.getMessage());
        }
        return configConnectionList;
    }

    /**
     * Gets the source config connection list.
     *
     * @return the source config connection list
     * @throws BODSException the bODS exception
     */
    public ArrayList<ConfiguratorVO> getSourceConfigConnectionList() throws BODSException {
        ConfiguratorDAO configuratorDAO = null;
        ArrayList<ConfiguratorVO> sourceConfigConnectionList = null;
        try {
            configuratorDAO = new ConfiguratorDAO();
            sourceConfigConnectionList = configuratorDAO.getSourceConfigConnectionList();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("Configurator", "getSourceConfigConnectionList", exception.getMessage());
        }
        return sourceConfigConnectionList;
    }

    /**
     * Gets the source configuration name list.
     *
     * @return the source configuration name list
     * @throws BODSException the bODS exception
     */
    public ArrayList<ConfiguratorVO> getSourceConfigurationNameList() throws BODSException {
        ConfiguratorDAO configuratorDAO = null;
        ArrayList<ConfiguratorVO> sourceConfigurationNameList = null;
        try {
            configuratorDAO = new ConfiguratorDAO();
            sourceConfigurationNameList = configuratorDAO.getSourceConfigurationNameList();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("Configurator", "getSourceConfigurationNameList", exception.getMessage());
        }
        return sourceConfigurationNameList;
    }

    /**
     * Save configurator details.
     *
     * @param configuratorVO the configurator vo
     * @throws BODSException the bODS exception
     */
    public void saveConfiguratorDetails(ConfiguratorVO configuratorVO) throws BODSException {
        ConfiguratorDAO configuratorDAO = null;
        ConfiguratorEO configuratorEO = null;
        ConfiguratorBinariesEO configuratorBinariesEO = null;
        ConfiguratorProcedureCreation configuratorProcedureCreation = null;
        BytesUtil bytesUtil = null;
        SpringBeanUtils beanUtils = null;
        Set<ConfiguratorBinariesEO> configuratorBinariesEOSet = null;
        try {
            bytesUtil = new BytesUtil();
            beanUtils = new SpringBeanUtils();
            configuratorBinariesEOSet = new HashSet<ConfiguratorBinariesEO>();
            configuratorProcedureCreation = new ConfiguratorProcedureCreation();
            configuratorDAO = new ConfiguratorDAO();
            configuratorEO = new ConfiguratorEO();
            configuratorBinariesEO = new ConfiguratorBinariesEO();
            configuratorProcedureCreation.createStagingProcedure(configuratorVO);
            configuratorProcedureCreation.createValidationProcedure(configuratorVO);
            configuratorBinariesEO.setObject(bytesUtil.toByteArray(configuratorVO));
            configuratorEO = (ConfiguratorEO) beanUtils.populateToEntityObject(configuratorVO, configuratorEO);
            configuratorBinariesEOSet.add(configuratorBinariesEO);
            configuratorEO.setConfiguratorBinariesEOSet(configuratorBinariesEOSet);
            configuratorEO = configuratorDAO.saveConfiguratorDetails(configuratorEO);
            if (configuratorEO.getConfiguratorId() > 0) {
                configuratorVO.setConfiguratorId(configuratorEO.getConfiguratorId());
                createConfiguratorDynamicTable(configuratorVO);
            }
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("Configurator", "getSourceConfigurationNameList", exception.getMessage());
        }
    }

    /**
     * Fetch configurator list.
     *
     * @param configuratorVO the configurator vo
     * @throws BODSException the bODS exception
     */
    public void fetchConfiguratorList(ConfiguratorVO configuratorVO) throws BODSException {
        ConfiguratorDAO configuratorDAO = null;
        try {
            configuratorDAO = new ConfiguratorDAO();
            configuratorDAO.fetchConfiguratorList(configuratorVO);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BODSException("Configurator", "fetchQueryDefinitionList", exception.getMessage());
        }
    }

    /**
     * Fetch configuration details.
     *
     * @param configuratorVO the configurator vo
     * @return the configurator vo
     * @throws BODSException the bODS exception
     */
    public ConfiguratorVO fetchConfigurationDetails(ConfiguratorVO configuratorVO) throws BODSException {
        ConfiguratorDAO configuratorDAO = null;
        ConfiguratorEO configuratorEO = null;
        BytesUtil bytesUtil = null;
        try {
            configuratorDAO = new ConfiguratorDAO();
            bytesUtil = new BytesUtil();
            configuratorEO = configuratorDAO.fetchConfigurator(configuratorVO.getConfiguratorId());
            configuratorVO =
                (ConfiguratorVO) bytesUtil.toObject(configuratorEO.getConfiguratorBinariesEOSet().iterator().next()
                    .getObject());

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BODSException("Configurator", "fetchConfigurationDetails", exception.getMessage());
        }
        return configuratorVO;
    }

    /**
     * Gets the staging table detail.
     *
     * @param stagingTableName the staging table name
     * @param dataTemplateName the data template name
     * @return the staging table detail
     * @throws BODSException the bODS exception
     */
    public String getStagingTableDetail(String stagingTableName, String dataTemplateName) throws BODSException {
        String chartValueStr = "";
        String chartContent = "";
        ConfiguratorDAO configuratorDAO = null;
        try {
            configuratorDAO = new ConfiguratorDAO();
            chartValueStr = configuratorDAO.getStagingTableDetailForPieChart(stagingTableName);
            chartContent = generatePieChart(dataTemplateName, chartValueStr);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("Configurator", "getStagingTableDetail", exception.getMessage());
        }
        return chartContent;
    }

    /**
     * Generate pie chart.
     *
     * @param dataTemplate the data template
     * @param chartValueStr the chart value str
     * @return the string
     * @throws BODSException the bODS exception
     */
    private String generatePieChart(String dataTemplate, String chartValueStr) throws BODSException {
        String chartName = "";
        String[] chartValueArr = null;
        BufferedImage bufferedImage;
        DefaultCategoryDataset dataSet = null;
        try {
            chartValueArr = chartValueStr.split("##");
            dataSet = new DefaultCategoryDataset();
            dataSet.setValue(new Double(chartValueArr[0]), "Total Count", "Total");
            dataSet.setValue(new Double(chartValueArr[1]), "Success Count", "Success");
            dataSet.setValue(new Double(chartValueArr[2]), "Error Count", "Error");
            final JFreeChart chart =
                ChartFactory.createBarChart(dataTemplate, "Record Type ", "Total Count", dataSet,
                    PlotOrientation.VERTICAL, true, true, false);
            chart.setBorderVisible(false);
            final CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.lightGray);
            plot.setDomainGridlinePaint(Color.white);
            plot.setRangeGridlinePaint(Color.white);
            final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setDrawBarOutline(true);
            final GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f, 0.0f, Color.lightGray);
            final GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, Color.lightGray);
            final GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f, 0.0f, Color.lightGray);
            renderer.setSeriesPaint(0, gp0);
            renderer.setSeriesPaint(1, gp1);
            renderer.setSeriesPaint(2, gp2);
            final CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
            chart.getPlot().setBackgroundPaint(Color.WHITE);
            bufferedImage = chart.createBufferedImage(350, 280);
            chartName = ImageUtils.getImageAsStringBase64Encoder(bufferedImage);
        } catch (Exception exeException) {
            throw new BODSException("Configurator", "generatePieChart", exeException.getMessage());
        }
        return chartName;
    }

    /**
     * Gets the prevalidation detail.
     *
     * @param stagingTableName the staging table name
     * @return the prevalidation detail
     * @throws BODSException the bODS exception
     */
    public String getPrevalidationDetail(String stagingTableName) throws BODSException {
        int totalRec = 0;
        int successRec = 0;
        int errorRec = 0;
        String totalRecord = "";
        String prevalStr = "";
        JSONObject valueObject = null;
        JSONObject prevalidationVOJSON = null;
        ConfiguratorDAO configuratorDAO = null;
        List<PrevalidationStatusVO> prevalidationStatusVOList = null;
        try {
            valueObject = new JSONObject();
            prevalidationVOJSON = new JSONObject();
            configuratorDAO = new ConfiguratorDAO();
            prevalidationStatusVOList = configuratorDAO.getPrevalidationStatus(stagingTableName);
            for (PrevalidationStatusVO prevalidationStatusVO1 : prevalidationStatusVOList) {
                totalRecord = prevalidationStatusVO1.getErrorCount() + prevalidationStatusVO1.getSuccessCount();
                totalRec = Integer.parseInt(totalRecord);
                successRec = Integer.parseInt(prevalidationStatusVO1.getSuccessCount());
                errorRec = Integer.parseInt(prevalidationStatusVO1.getErrorCount());
                prevalidationStatusVO1.setSuccessPrecentage(getPercentage(totalRec, successRec));
                prevalidationStatusVO1.setErrorPrecentage(getPercentage(totalRec, errorRec));
            }
            valueObject.append("prevalidationStatusVO", prevalidationStatusVOList);
            prevalidationVOJSON.put("configuratorVO", valueObject);
            prevalStr = prevalidationVOJSON.toString();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("Configurator", "getPrevalidationDetail", exception.getMessage());
        }
        return prevalStr;
    }

    /**
     * Gets the percentage.
     *
     * @param total the total
     * @param count the count
     * @return the percentage
     * @throws BODSException the bODS exception
     */
    public String getPercentage(int total, int count) throws BODSException {
        String percentage = "";
        try {
            if (total != 0) {
                BigDecimal num1 = new BigDecimal(count);
                BigDecimal num2 = new BigDecimal(total);
                BigDecimal num3 = num1.divide(num2, 2, BigDecimal.ROUND_HALF_EVEN);
                BigDecimal num4 = new BigDecimal(100);
                BigDecimal num5 = num3.multiply(num4);
                percentage = num5.toString();
            }
        } catch (Exception exception) {
            throw new BODSException("Configurator", "getPercentage", exception.getMessage());
        }
        return percentage;
    }

}
