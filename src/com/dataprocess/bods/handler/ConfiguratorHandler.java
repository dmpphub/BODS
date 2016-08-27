package com.dataprocess.bods.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.dataprocess.bods.business.Configurator;
import com.dataprocess.bods.business.ConfiguratorExecutor;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.vo.ConfiguratorColumnDefinitionVO;
import com.dataprocess.bods.vo.ConfiguratorInterfaceColumnVO;
import com.dataprocess.bods.vo.ConfiguratorVO;

/**
 * The Class ConfiguratorHandler.
 */
public final class ConfiguratorHandler {

    /**
     * Gets the source configurator column.
     * 
     * @param configuratorVO the configurator vo
     * @return the source configurator column
     * @throws BODSException the bODS exception
     */
    public ConfiguratorVO getSourceConfiguratorColumn(ConfiguratorVO configuratorVO) throws BODSException {
        List<ConfiguratorColumnDefinitionVO> configuratorColDefnList = null;
        Set<ConfiguratorColumnDefinitionVO> configuratorColumnDefinitionVOSet = null;
        Configurator configurator = null;
        try {
            configurator = new Configurator();
            configuratorColDefnList =
                configurator.getSourceConfiguratorColumn(configuratorVO.getSourceConfigurationId());
            configuratorColumnDefinitionVOSet = new HashSet<ConfiguratorColumnDefinitionVO>(configuratorColDefnList);
            configuratorVO.setConfiguratorColumnDefinitionVOSet(configuratorColumnDefinitionVOSet);
            configuratorVO.setConfiguratorColumnDefinitionVOList(configuratorColDefnList);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorHandler", "getSourceConfiguratorColumn", exception.getMessage());
        }
        return configuratorVO;
    }

    /**
     * Gets the interface table column info.
     * 
     * @param configuratorVO the configurator vo
     * @return the interface table column info
     * @throws BODSException the bODS exception
     */
    public JSONObject getInterfaceTableColumnInfo(ConfiguratorVO configuratorVO) throws BODSException {
        JSONObject valueObject = null;
        Configurator configurator = null;
        List<ConfiguratorInterfaceColumnVO> configuratorInterfaceColumnVOList = null;
        try {
            valueObject = new JSONObject();
            configurator = new Configurator();
            configuratorInterfaceColumnVOList = configurator.getInterfaceTableColumnList(configuratorVO);
            valueObject.put("configuratorTableColVOList", configuratorInterfaceColumnVOList);
            valueObject.put("configuratorAttributeColVOList", configuratorVO.getConfiguratorColumnDefinitionVOList());
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorHandler", "getSourceConfiguratorColumn", exception.getMessage());
        }
        return valueObject;
    }

    /**
     * Parses the query.
     *
     * @param configuratorVO the configurator vo
     * @return the string
     * @throws BODSException the bODS exception
     */
    public JSONObject parseQuery(ConfiguratorVO configuratorVO) throws BODSException {
        String jsonString = "";
        JSONObject valueObject = null;
        Configurator configurator = null;
        try {
            configurator = new Configurator();
            jsonString = configurator.parseQuery(configuratorVO);
            valueObject = new JSONObject(jsonString);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorHandler", "getSourceConfiguratorColumn", exception.getMessage());
        }
        return valueObject;

    }

    /**
     * Gets the config connection list.
     *
     * @return the config connection list
     * @throws BODSException the bODS exception
     */
    public ArrayList<ConfiguratorVO> getConfigConnectionList() throws BODSException {
        Configurator configurator = null;
        ArrayList<ConfiguratorVO> configConnectionList = null;
        try {
            configurator = new Configurator();
            configConnectionList = configurator.getConfigConnectionList();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorHandler", "getConfigConnectionList", exception.getMessage());
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
        ArrayList<ConfiguratorVO> sourceConfigConnectionList = null;
        Configurator configurator = null;
        try {
            configurator = new Configurator();
            sourceConfigConnectionList = configurator.getSourceConfigConnectionList();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorHandler", "getSourceConfigConnectionList", exception.getMessage());
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
        ArrayList<ConfiguratorVO> sourceConfigurationNameList = null;
        Configurator configurator = null;
        try {
            configurator = new Configurator();
            sourceConfigurationNameList = configurator.getSourceConfigurationNameList();
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorHandler", "getSourceConfigurationNameList", exception.getMessage());
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
        Configurator configurator = null;
        try {
            configurator = new Configurator();
            configurator.saveConfiguratorDetails(configuratorVO);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorHandler", "getSourceConfigurationNameList", exception.getMessage());
        }
    }

    /**
     * Execute.
     *
     * @param configuratorId the configurator id
     * @param configuratorConnId the configurator conn id
     * @throws BODSException the bODS exception
     */
    public void execute(int configuratorId, int configuratorConnId) throws BODSException {
        ConfiguratorExecutor configuratorExecutor = null;
        try {
            configuratorExecutor = new ConfiguratorExecutor();
            configuratorExecutor.loaderStagingTableInsert(configuratorId, configuratorConnId);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorHandler", "execute", exception.getMessage());
        }

    }

    /**
     * Fetch configurator list.
     *
     * @param configuratorVO the configurator vo
     * @return the configurator vo
     * @throws BODSException the bODS exception
     */
    public ConfiguratorVO fetchConfiguratorList(ConfiguratorVO configuratorVO) throws BODSException {
        Configurator configurator = null;
        try {
            configurator = new Configurator();
            configurator.fetchConfiguratorList(configuratorVO);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BODSException("ConfiguratorVO", "fetchQueryDefinitionList", e.getMessage());
        }
        return configuratorVO;
    }

    /**
     * Fetch configuration details.
     *
     * @param configuratorVO the configurator vo
     * @return the configurator vo
     * @throws BODSException the bODS exception
     */
    public ConfiguratorVO fetchConfigurationDetails(ConfiguratorVO configuratorVO) throws BODSException {
        Configurator configurator = null;
        try {
            configurator = new Configurator();
            configuratorVO = configurator.fetchConfigurationDetails(configuratorVO);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BODSException("ConfiguratorVO", "fetchConfigurationDetails", exception.getMessage());
        }
        return configuratorVO;
    }

    /**
     * Gets the stagint table detail.
     *
     * @param stagingTableName the staging table name
     * @param dataTemplateName the data template name
     * @return the stagint table detail
     * @throws BODSException the bODS exception
     */
    public String getStagintTableDetail(String stagingTableName, String dataTemplateName) throws BODSException {
        String chartContent = "";
        Configurator configurator = null;
        try {
            configurator = new Configurator();
            chartContent = configurator.getStagingTableDetail(stagingTableName, dataTemplateName);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorVO", "getStagintTableDetail", exception.getMessage());
        }
        return chartContent;
    }

    /**
     * Gets the prevalidation detail.
     *
     * @param stagingTableName the staging table name
     * @return the prevalidation detail
     * @throws BODSException the bODS exception
     */
    public JSONObject getPrevalidationDetail(String stagingTableName) throws BODSException {
        String prevalContent = "";
        JSONObject valueObject = null;
        Configurator configurator = null;
        try {
            valueObject = new JSONObject();
            configurator = new Configurator();
            prevalContent = configurator.getPrevalidationDetail(stagingTableName);
            valueObject = new JSONObject(prevalContent);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorVO", "getStagintTableDetail", exception.getMessage());
        }
        return valueObject;
    }

    /**
     * Execute staging procedure.
     *
     * @throws BODSException the bODS exception
     */
    public void executeStagingProcedure(ConfiguratorVO configuratorVO) throws BODSException {
        int configuratorId = 0;
        int configuratorConnId = 0;
        ConfiguratorExecutor configuratorExecutor = null;
        try {
            configuratorExecutor = new ConfiguratorExecutor();
            configuratorId = configuratorVO.getConfiguratorId();
            configuratorConnId = configuratorVO.getConfiguratorConnectionId();
            configuratorExecutor.callStagingProcess(configuratorId, configuratorConnId);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorVO", "getStagintTableDetail", exception.getMessage());
        }
    }
}
