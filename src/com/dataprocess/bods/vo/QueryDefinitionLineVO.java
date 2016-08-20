package com.dataprocess.bods.vo;

import java.io.Serializable;

import com.dataprocess.bods.util.EntityProperty;

public class QueryDefinitionLineVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4166010645206478963L;

	@EntityProperty(columnName = "sourceConfiguratorLineId")
	private int sourceConfiguratorLineId;
	
	@EntityProperty(columnName = "sourceConfiguratorId")
	private int sourceConfiguratorId;
	
	@EntityProperty(columnName = "columnName")
	private String columnName;
	
	@EntityProperty(columnName = "dataType")
	private String dataType;
	
	@EntityProperty(columnName = "columnMandatoryFlag")
	private String columnMandatoryFlag;
	
	@EntityProperty(columnName = "columnUniqueFlag")
	private String columnUniqueFlag;
	
	@EntityProperty(columnName = "conversionColumn")
	private String conversionColumn;
	
	private String dataValue;

	public int getSourceConfiguratorLineId() {
		return sourceConfiguratorLineId;
	}
	
	public void setSourceConfiguratorLineId(int sourceConfiguratorLineId) {
		this.sourceConfiguratorLineId = sourceConfiguratorLineId;
	}
	
	public int getSourceConfiguratorId() {
		return sourceConfiguratorId;
	}
	
	public void setSourceConfiguratorId(int sourceConfiguratorId) {
		this.sourceConfiguratorId = sourceConfiguratorId;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getColumnMandatoryFlag() {
		return columnMandatoryFlag;
	}
	
	public void setColumnMandatoryFlag(String columnMandatoryFlag) {
		this.columnMandatoryFlag = columnMandatoryFlag;
	}
	
	public String getColumnUniqueFlag() {
		return columnUniqueFlag;
	}
	
	public void setColumnUniqueFlag(String columnUniqueFlag) {
		this.columnUniqueFlag = columnUniqueFlag;
	}
	
	public String getConversionColumn() {
		return conversionColumn;
	}
	
	public void setConversionColumn(String conversionColumn) {
		this.conversionColumn = conversionColumn;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

}