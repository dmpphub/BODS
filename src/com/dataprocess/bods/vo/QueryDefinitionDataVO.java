/*
 * 
 */
package com.dataprocess.bods.vo;

import java.io.Serializable;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryDefinitionDataVO.
 */
public class QueryDefinitionDataVO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5093679475070831768L;

    /** The data value. */
    private String dataValue;

    /** The unique flag. */
    private String uniqueFlag;

    /** The query definition data vo list. */
    private List<QueryDefinitionDataVO> queryDefinitionDataVOList;

    /**
     * Gets the data value.
     *
     * @return the data value
     */
    public String getDataValue() {
        return dataValue;
    }

    /**
     * Sets the data value.
     *
     * @param dataValue the new data value
     */
    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    /**
     * Gets the unique flag.
     *
     * @return the unique flag
     */
    public String getUniqueFlag() {
        return uniqueFlag;
    }

    /**
     * Sets the unique flag.
     *
     * @param uniqueFlag the new unique flag
     */
    public void setUniqueFlag(String uniqueFlag) {
        this.uniqueFlag = uniqueFlag;
    }

    /**
     * Gets the query definition data vo list.
     *
     * @return the query definition data vo list
     */
    public List<QueryDefinitionDataVO> getQueryDefinitionDataVOList() {
        return queryDefinitionDataVOList;
    }

    /**
     * Sets the query definition data vo list.
     *
     * @param queryDefinitionDataVOList the new query definition data vo list
     */
    public void setQueryDefinitionDataVOList(List<QueryDefinitionDataVO> queryDefinitionDataVOList) {
        this.queryDefinitionDataVOList = queryDefinitionDataVOList;
    }

}
