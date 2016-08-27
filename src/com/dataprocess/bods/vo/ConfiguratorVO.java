package com.dataprocess.bods.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.list.LazyList;

import com.dataprocess.bods.util.EntityProperty;
import com.dataprocess.bods.util.ListFactoryVO;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfiguratorVO.
 */
public class ConfiguratorVO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7844236478716570078L;

    /** The configurator id. */
    @EntityProperty(columnName = "configuratorId")
    private int configuratorId;

    /** The configurator connection id. */
    @EntityProperty(columnName = "configuratorConnectionId")
    private int configuratorConnectionId;

    /** The configurator name. */
    @EntityProperty(columnName = "configuratorName")
    private String configuratorName;

    /** The configurator connection name. */
    @EntityProperty(columnName = "configuratorConnectionName")
    private String configuratorConnectionName;

    /** The display name. */
    @EntityProperty(columnName = "displayName")
    private String displayName;

    /** The description. */
    @EntityProperty(columnName = "description")
    private String description;

    /** The redundant reducer. */
    @EntityProperty(columnName = "redundantReducer")
    private String redundantReducer;

    /** The conversion. */
    @EntityProperty(columnName = "conversion")
    private String conversion;

    /** The source config connection list. */
    private ArrayList<ConfiguratorVO> sourceConfigConnectionList;

    /** The config connection list. */
    private ArrayList<ConfiguratorVO> configConnectionList;

    /** The source configuration name list. */
    private ArrayList<ConfiguratorVO> sourceConfigurationNameList;

    /* Column Definition Header Content and Column Definition */

    /** The source configuration connection id. */
    @EntityProperty(columnName = "sourceConfigurationConnectionId")
    private int sourceConfigurationConnectionId;

    /** The source configuration id. */
    @EntityProperty(columnName = "sourceConfigurationId")
    private int sourceConfigurationId;

    /** The source configuration connection name. */
    @EntityProperty(columnName = "sourceConfigurationConnectionName")
    private String sourceConfigurationConnectionName;

    /** The source configuration name. */
    @EntityProperty(columnName = "sourceConfigurationName")
    private String sourceConfigurationName;

    /** The procedure value. */
    private String procedureValue;

    /** The configurator column definition vo list. */
    private List<ConfiguratorColumnDefinitionVO> configuratorColumnDefinitionVOList;

    /** The configurator column definition vo set. */
    @EntityProperty(columnName = "configuratorColumnDefinitionEOSet", entity = "com.dataprocess.bods.entity.ConfiguratorColumnDefinitionEO")
    private Set<ConfiguratorColumnDefinitionVO> configuratorColumnDefinitionVOSet;

    /* Interface Column Definition */

    /** The configurator interface column vo. */
    private ConfiguratorInterfaceColumnVO configuratorInterfaceColumnVO;

    /** The configurator interface column vo list. */
    private List<ConfiguratorInterfaceColumnVO> configuratorInterfaceColumnVOList;

    /* Concurrent Program Definition */

    /** The concurrent program name. */
    private String concurrentProgramName;

    /** The concurrent parameter vo list. */
    private List<ConcurrentParameterVO> concurrentParameterVOList;

    /* Validation Process Definition */

    /** The current configurator validation vo. */
    private ConfiguratorValidationVO configuratorValidationVO;

    /** The configurator validation vo list. */
    private List<ConfiguratorValidationVO> configuratorValidationVOList;

    /** The configurator vo list. */
    private List<ConfiguratorVO> configuratorVOList;

    /** The preval data string. */
    private String prevalDataString;

    /** The staging table name. */
    private List<String> prevalidationSeqNo;

    /**
     * Instantiates a new configurator vo.
     */
    public ConfiguratorVO() {
        configuratorValidationVO = new ConfiguratorValidationVO();
        configuratorInterfaceColumnVO = new ConfiguratorInterfaceColumnVO();
        configuratorInterfaceColumnVOList = new ArrayList<ConfiguratorInterfaceColumnVO>();
        initializeDynamicConfiguratorInterfaceColumnList();
    }

    /**
     * Initialize dynamic cross ref column list.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void initializeDynamicConfiguratorInterfaceColumnList() {
        ListFactoryVO factory = new ListFactoryVO(ConfiguratorInterfaceColumnVO.class);
        configuratorInterfaceColumnVOList = LazyList.decorate(new ArrayList(), factory);

    }

    /**
     * Gets the configurator id.
     * 
     * @return the configurator id
     */
    public int getConfiguratorId() {
        return configuratorId;
    }

    /**
     * Sets the configurator id.
     * 
     * @param configuratorId the new configurator id
     */
    public void setConfiguratorId(int configuratorId) {
        this.configuratorId = configuratorId;
    }

    /**
     * Gets the configurator connection id.
     * 
     * @return the configurator connection id
     */
    public int getConfiguratorConnectionId() {
        return configuratorConnectionId;
    }

    /**
     * Sets the configurator connection id.
     * 
     * @param configuratorConnectionId the new configurator connection id
     */
    public void setConfiguratorConnectionId(int configuratorConnectionId) {
        this.configuratorConnectionId = configuratorConnectionId;
    }

    /**
     * Gets the configurator name.
     * 
     * @return the configurator name
     */
    public String getConfiguratorName() {
        return configuratorName;
    }

    /**
     * Sets the configurator name.
     * 
     * @param configuratorName the new configurator name
     */
    public void setConfiguratorName(String configuratorName) {
        this.configuratorName = configuratorName;
    }

    /**
     * Gets the configurator connection name.
     * 
     * @return the configurator connection name
     */
    public String getConfiguratorConnectionName() {
        return configuratorConnectionName;
    }

    /**
     * Sets the configurator connection name.
     * 
     * @param configuratorConnectionName the new configurator connection name
     */
    public void setConfiguratorConnectionName(String configuratorConnectionName) {
        this.configuratorConnectionName = configuratorConnectionName;
    }

    /**
     * Gets the display name.
     * 
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name.
     * 
     * @param displayName the new display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the redundant reducer.
     * 
     * @return the redundant reducer
     */
    public String getRedundantReducer() {
        return redundantReducer;
    }

    /**
     * Sets the redundant reducer.
     * 
     * @param redundantReducer the new redundant reducer
     */
    public void setRedundantReducer(String redundantReducer) {
        this.redundantReducer = redundantReducer;
    }

    /**
     * Gets the conversion.
     * 
     * @return the conversion
     */
    public String getConversion() {
        return conversion;
    }

    /**
     * Sets the conversion.
     * 
     * @param conversion the new conversion
     */
    public void setConversion(String conversion) {
        this.conversion = conversion;
    }

    /**
     * Gets the source configuration connection id.
     * 
     * @return the source configuration connection id
     */
    public int getSourceConfigurationConnectionId() {
        return sourceConfigurationConnectionId;
    }

    /**
     * Sets the source configuration connection id.
     * 
     * @param sourceConfigurationConnectionId the new source configuration connection id
     */
    public void setSourceConfigurationConnectionId(int sourceConfigurationConnectionId) {
        this.sourceConfigurationConnectionId = sourceConfigurationConnectionId;
    }

    /**
     * Gets the source configuration id.
     * 
     * @return the source configuration id
     */
    public int getSourceConfigurationId() {
        return sourceConfigurationId;
    }

    /**
     * Sets the source configuration id.
     * 
     * @param sourceConfigurationId the new source configuration id
     */
    public void setSourceConfigurationId(int sourceConfigurationId) {
        this.sourceConfigurationId = sourceConfigurationId;
    }

    /**
     * Gets the source configuration connection name.
     * 
     * @return the source configuration connection name
     */
    public String getSourceConfigurationConnectionName() {
        return sourceConfigurationConnectionName;
    }

    /**
     * Sets the source configuration connection name.
     * 
     * @param sourceConfigurationConnectionName the new source configuration connection name
     */
    public void setSourceConfigurationConnectionName(String sourceConfigurationConnectionName) {
        this.sourceConfigurationConnectionName = sourceConfigurationConnectionName;
    }

    /**
     * Gets the source configuration name.
     * 
     * @return the source configuration name
     */
    public String getSourceConfigurationName() {
        return sourceConfigurationName;
    }

    /**
     * Sets the source configuration name.
     * 
     * @param sourceConfigurationName the new source configuration name
     */
    public void setSourceConfigurationName(String sourceConfigurationName) {
        this.sourceConfigurationName = sourceConfigurationName;
    }

    /**
     * Gets the configurator column definition vo list.
     * 
     * @return the configurator column definition vo list
     */
    public List<ConfiguratorColumnDefinitionVO> getConfiguratorColumnDefinitionVOList() {
        return configuratorColumnDefinitionVOList;
    }

    /**
     * Sets the configurator column definition vo list.
     * 
     * @param configuratorColumnDefinitionVOList the new configurator column definition vo list
     */
    public void setConfiguratorColumnDefinitionVOList(
        List<ConfiguratorColumnDefinitionVO> configuratorColumnDefinitionVOList) {
        this.configuratorColumnDefinitionVOList = configuratorColumnDefinitionVOList;
    }

    /**
     * Gets the configurator column definition vo set.
     *
     * @return the configurator column definition vo set
     */
    public Set<ConfiguratorColumnDefinitionVO> getConfiguratorColumnDefinitionVOSet() {
        return configuratorColumnDefinitionVOSet;
    }

    /**
     * Sets the configurator column definition vo set.
     *
     * @param configuratorColumnDefinitionVOSet the new configurator column definition vo set
     */
    public void setConfiguratorColumnDefinitionVOSet(
        Set<ConfiguratorColumnDefinitionVO> configuratorColumnDefinitionVOSet) {
        this.configuratorColumnDefinitionVOSet = configuratorColumnDefinitionVOSet;
    }

    /**
     * Gets the configurator interface column vo list.
     * 
     * @return the configurator interface column vo list
     */
    public List<ConfiguratorInterfaceColumnVO> getConfiguratorInterfaceColumnVOList() {
        return configuratorInterfaceColumnVOList;
    }

    /**
     * Sets the configurator interface column vo list.
     * 
     * @param configuratorInterfaceColumnVOList the new configurator interface column vo list
     */
    public void setConfiguratorInterfaceColumnVOList(
        List<ConfiguratorInterfaceColumnVO> configuratorInterfaceColumnVOList) {
        this.configuratorInterfaceColumnVOList = configuratorInterfaceColumnVOList;
    }

    /**
     * Gets the concurrent program name.
     * 
     * @return the concurrent program name
     */
    public String getConcurrentProgramName() {
        return concurrentProgramName;
    }

    /**
     * Sets the concurrent program name.
     * 
     * @param concurrentProgramName the new concurrent program name
     */
    public void setConcurrentProgramName(String concurrentProgramName) {
        this.concurrentProgramName = concurrentProgramName;
    }

    /**
     * Gets the concurrent parameter vo list.
     * 
     * @return the concurrent parameter vo list
     */
    public List<ConcurrentParameterVO> getConcurrentParameterVOList() {
        return concurrentParameterVOList;
    }

    /**
     * Sets the concurrent parameter vo list.
     * 
     * @param concurrentParameterVOList the new concurrent parameter vo list
     */
    public void setConcurrentParameterVOList(List<ConcurrentParameterVO> concurrentParameterVOList) {
        this.concurrentParameterVOList = concurrentParameterVOList;
    }

    /**
     * Gets the configurator validation vo list.
     *
     * @return the configurator validation vo list
     */
    public List<ConfiguratorValidationVO> getConfiguratorValidationVOList() {
        return configuratorValidationVOList;
    }

    /**
     * Sets the configurator validation vo list.
     *
     * @param configuratorValidationVOList the new configurator validation vo list
     */
    public void setConfiguratorValidationVOList(List<ConfiguratorValidationVO> configuratorValidationVOList) {
        this.configuratorValidationVOList = configuratorValidationVOList;
    }

    /**
     * Gets the config connection list.
     *
     * @return the config connection list
     */
    public ArrayList<ConfiguratorVO> getConfigConnectionList() {
        return configConnectionList;
    }

    /**
     * Sets the config connection list.
     *
     * @param configConnectionList the new config connection list
     */
    public void setConfigConnectionList(ArrayList<ConfiguratorVO> configConnectionList) {
        this.configConnectionList = configConnectionList;
    }

    /**
     * Gets the source config connection list.
     *
     * @return the source config connection list
     */
    public ArrayList<ConfiguratorVO> getSourceConfigConnectionList() {
        return sourceConfigConnectionList;
    }

    /**
     * Sets the source config connection list.
     *
     * @param sourceConfigConnectionList the new source config connection list
     */
    public void setSourceConfigConnectionList(ArrayList<ConfiguratorVO> sourceConfigConnectionList) {
        this.sourceConfigConnectionList = sourceConfigConnectionList;
    }

    /**
     * Gets the source configuration name list.
     *
     * @return the source configuration name list
     */
    public ArrayList<ConfiguratorVO> getSourceConfigurationNameList() {
        return sourceConfigurationNameList;
    }

    /**
     * Sets the source configuration name list.
     *
     * @param sourceConfigurationNameList the new source configuration name list
     */
    public void setSourceConfigurationNameList(ArrayList<ConfiguratorVO> sourceConfigurationNameList) {
        this.sourceConfigurationNameList = sourceConfigurationNameList;
    }

    /**
     * Gets the procedure value.
     *
     * @return the procedure value
     */
    public String getProcedureValue() {
        return procedureValue;
    }

    /**
     * Sets the procedure value.
     *
     * @param procedureValue the new procedure value
     */
    public void setProcedureValue(String procedureValue) {
        this.procedureValue = procedureValue;
    }

    /**
     * Gets the configurator validation vo.
     *
     * @return the configurator validation vo
     */
    public ConfiguratorValidationVO getConfiguratorValidationVO() {
        return configuratorValidationVO;
    }

    /**
     * Sets the configurator validation vo.
     *
     * @param configuratorValidationVO the new configurator validation vo
     */
    public void setConfiguratorValidationVO(ConfiguratorValidationVO configuratorValidationVO) {
        this.configuratorValidationVO = configuratorValidationVO;
    }

    /**
     * Gets the configurator interface column vo.
     *
     * @return the configurator interface column vo
     */
    public ConfiguratorInterfaceColumnVO getConfiguratorInterfaceColumnVO() {
        return configuratorInterfaceColumnVO;
    }

    /**
     * Sets the configurator interface column vo.
     *
     * @param configuratorInterfaceColumnVO the new configurator interface column vo
     */
    public void setConfiguratorInterfaceColumnVO(ConfiguratorInterfaceColumnVO configuratorInterfaceColumnVO) {
        this.configuratorInterfaceColumnVO = configuratorInterfaceColumnVO;
    }

    /**
     * Gets the configurator vo list.
     *
     * @return the configurator vo list
     */
    public List<ConfiguratorVO> getConfiguratorVOList() {
        return configuratorVOList;
    }

    /**
     * Sets the configurator vo list.
     *
     * @param configuratorVOList the new configurator vo list
     */
    public void setConfiguratorVOList(List<ConfiguratorVO> configuratorVOList) {
        this.configuratorVOList = configuratorVOList;
    }

    /**
     * Gets the preval data string.
     *
     * @return the preval data string
     */
    public String getPrevalDataString() {
        return prevalDataString;
    }

    /**
     * Sets the preval data string.
     *
     * @param prevalDataString the new preval data string
     */
    public void setPrevalDataString(String prevalDataString) {
        this.prevalDataString = prevalDataString;
    }

    public List<String> getPrevalidationSeqNo() {
        return prevalidationSeqNo;
    }

    public void setPrevalidationSeqNo(List<String> prevalidationSeqNo) {
        this.prevalidationSeqNo = prevalidationSeqNo;
    }
}
