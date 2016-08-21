package com.dataprocess.bods.business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.dataprocess.bods.dao.ConfiguratorDAO;
import com.dataprocess.bods.dao.ConfiguratorValidationDAO;
import com.dataprocess.bods.entity.ConfiguratorBinariesEO;
import com.dataprocess.bods.entity.ConfiguratorEO;
import com.dataprocess.bods.entity.QueryDefinitionEO;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.util.BytesUtil;
import com.dataprocess.bods.util.SpringBeanUtils;
import com.dataprocess.bods.util.connectionutil.TargetSchemaConnection;
import com.dataprocess.bods.vo.ConfiguratorColumnDefinitionVO;
import com.dataprocess.bods.vo.ConfiguratorInterfaceColumnVO;
import com.dataprocess.bods.vo.ConfiguratorVO;
import com.dataprocess.bods.vo.QueryDefinitionLineVO;
import com.dataprocess.bods.vo.QueryDefinitionVO;

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
            stagingTableScript = "CREATE TABLE " + tableName;
            stagingTableScript += getColumnList(configuratorVO.getConfiguratorColumnDefinitionVOList());
            configuratorDAO.createStagingTable(connection, stagingTableScript, tableName);
            stagingTableScript = "";
            stagingTableScript += generatePVTableScripts(pvtableName);
            configuratorDAO.createPrevalidationTable(connection, stagingTableScript, pvtableName);
            configuratorDAO.insertPrevalidationValues(connection, pvtableName, configuratorVO);
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
     * @param dataBastType the data bast type
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
}
