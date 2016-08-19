package com.dataprocess.bods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class SourceConfiguratorLineEO.
 */
@Entity
@Table(name = "BODS_SOURCE_LINE_CFG")
public class SourceConfiguratorLineEO {

	/** The source configurator line id. */
	@SequenceGenerator(name = "generator", sequenceName = "SOURCE_CFG_LINE_ID_SEQ")
	@Id
	@Column(name = "SOURCE_CFG_LINE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	private int sourceConfiguratorLineId;

	/** The source configurator id. */
	@Column(name = "SOURCE_CFG_ID")
	private int sourceConfiguratorId;

	/** The column name. */
	@Column(name = "SOURCE_CFG_COLUMN_NAME")
	private String columnName;

	/** The data type. */
	@Column(name = "SOURCE_DATATYPE")
	private String dataType;

	/** The column mandatory flag. */
	@Column(name = "SOURCE_MANDATORY_COLUMN")
	private String columnMandatoryFlag;

	/** The column unique flag. */
	@Column(name = "SOURCE_UNIQUE_COLUMN")
	private String columnUniqueFlag;

	/** The conversion column. */
	@Column(name = "SOURCE_CONVERSION_COLUMN")
	private String conversionColumn;

	/**
	 * Gets the source configurator line id.
	 *
	 * @return the source configurator line id
	 */
	public int getSourceConfiguratorLineId() {
		return sourceConfiguratorLineId;
	}

	/**
	 * Sets the source configurator line id.
	 *
	 * @param sourceConfiguratorLineId the new source configurator line id
	 */
	public void setSourceConfiguratorLineId(int sourceConfiguratorLineId) {
		this.sourceConfiguratorLineId = sourceConfiguratorLineId;
	}

	/**
	 * Gets the source configurator id.
	 *
	 * @return the source configurator id
	 */
	public int getSourceConfiguratorId() {
		return sourceConfiguratorId;
	}

	/**
	 * Sets the source configurator id.
	 *
	 * @param sourceConfiguratorId the new source configurator id
	 */
	public void setSourceConfiguratorId(int sourceConfiguratorId) {
		this.sourceConfiguratorId = sourceConfiguratorId;
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
	 * Gets the column mandatory flag.
	 *
	 * @return the column mandatory flag
	 */
	public String getColumnMandatoryFlag() {
		return columnMandatoryFlag;
	}

	/**
	 * Sets the column mandatory flag.
	 *
	 * @param columnMandatoryFlag the new column mandatory flag
	 */
	public void setColumnMandatoryFlag(String columnMandatoryFlag) {
		this.columnMandatoryFlag = columnMandatoryFlag;
	}

	/**
	 * Gets the column unique flag.
	 *
	 * @return the column unique flag
	 */
	public String getColumnUniqueFlag() {
		return columnUniqueFlag;
	}

	/**
	 * Sets the column unique flag.
	 *
	 * @param columnUniqueFlag the new column unique flag
	 */
	public void setColumnUniqueFlag(String columnUniqueFlag) {
		this.columnUniqueFlag = columnUniqueFlag;
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
}
