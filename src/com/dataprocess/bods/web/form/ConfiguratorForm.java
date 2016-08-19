package com.dataprocess.bods.web.form;

import org.apache.struts.action.ActionForm;

import com.dataprocess.bods.vo.ConfiguratorVO;

/**
 * The Class ConfiguratorForm.
 */
public class ConfiguratorForm extends ActionForm {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8478746882959582363L;

    /** The configurator vo. */
    private ConfiguratorVO configuratorVO;

    /**
     * Instantiates a new configurator form.
     */
    public ConfiguratorForm() {
        configuratorVO = new ConfiguratorVO();
    }

    /**
     * Gets the configurator vo.
     *
     * @return the configurator vo
     */
    public ConfiguratorVO getConfiguratorVO() {
        return configuratorVO;
    }

    /**
     * Sets the configurator vo.
     *
     * @param configuratorVO the new configurator vo
     */
    public void setConfiguratorVO(ConfiguratorVO configuratorVO) {
        this.configuratorVO = configuratorVO;
    }
}
