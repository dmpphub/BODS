package com.dataprocess.bods.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfiguratorInterfaceColumnVO.
 */
public final class ConfiguratorInterfaceColumnVO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1126216529846999419L;

    /** The table name. */
    private String tableName;

    /** The column name. */
    private String columnName;

    /** The attribute name. */
    private String attributeName;

    /** The mapped column flag. */
    private String mappedColumnFlag;

    /**
     * Gets the table name.
     *
     * @return the table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the table name.
     *
     * @param tableName the new table name
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
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
     * Gets the attribute name.
     *
     * @return the attribute name
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * Sets the attribute name.
     *
     * @param attributeName the new attribute name
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * Gets the mapped column flag.
     *
     * @return the mapped column flag
     */
    public String getMappedColumnFlag() {
        return mappedColumnFlag;
    }

    /**
     * Sets the mapped column flag.
     *
     * @param mappedColumnFlag the new mapped column flag
     */
    public void setMappedColumnFlag(String mappedColumnFlag) {
        this.mappedColumnFlag = mappedColumnFlag;
    }

}
