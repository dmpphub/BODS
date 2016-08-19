package com.dataprocess.bods.vo;

import java.io.Serializable;

import com.dataprocess.bods.util.EntityProperty;

/**
 * The Class ConfiguratorColumnDefinitionVO.
 */
public final class ConfiguratorColumnDefinitionVO implements Serializable {

    private static final long serialVersionUID = -8571476223629009441L;

    @EntityProperty(columnName = "columnId")
    private int columnId;

    @EntityProperty(columnName = "columnName")
    private String columnName;

    @EntityProperty(columnName = "dataType")
    private String dataType;

    @EntityProperty(columnName = "mandatory")
    private String mandatory;

    @EntityProperty(columnName = "unique")
    private String unique;

    @EntityProperty(columnName = "conversionColumn")
    private String conversionColumn;

    @EntityProperty(columnName = "activeColumnFlag")
    private String activeColumnFlag;

    /**
     * Gets the column id.
     * 
     * @return the column id
     */
    public int getColumnId() {
        return columnId;
    }

    /**
     * Sets the column id.
     * 
     * @param columnId the new column id
     */
    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    /**
     * Gets the column name.
     * 
     * @return the column name
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Sets the column name.
     * 
     * @param columnName the new column name
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Gets the data type.
     * 
     * @return the data type
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets the data type.
     * 
     * @param dataType the new data type
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * Gets the mandatory.
     * 
     * @return the mandatory
     */
    public String getMandatory() {
        return mandatory;
    }

    /**
     * Sets the mandatory.
     * 
     * @param mandatory the new mandatory
     */
    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * Gets the unique.
     * 
     * @return the unique
     */
    public String getUnique() {
        return unique;
    }

    /**
     * Sets the unique.
     * 
     * @param unique the new unique
     */
    public void setUnique(String unique) {
        this.unique = unique;
    }

    /**
     * Gets the conversion column.
     * 
     * @return the conversion column
     */
    public String getConversionColumn() {
        return conversionColumn;
    }

    /**
     * Sets the conversion column.
     * 
     * @param conversionColumn the new conversion column
     */
    public void setConversionColumn(String conversionColumn) {
        this.conversionColumn = conversionColumn;
    }

    /**
     * Gets the active column flag.
     * 
     * @return the active column flag
     */
    public String getActiveColumnFlag() {
        return activeColumnFlag;
    }

    /**
     * Sets the active column flag.
     * 
     * @param activeColumnFlag the new active column flag
     */
    public void setActiveColumnFlag(String activeColumnFlag) {
        this.activeColumnFlag = activeColumnFlag;
    }

}
