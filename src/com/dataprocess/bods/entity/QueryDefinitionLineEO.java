package com.dataprocess.bods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="BODS_SOURCE_LINE_CFG")
public class QueryDefinitionLineEO {
	
	@SequenceGenerator(name = "generator", sequenceName = "SOURCE_CFG_LINE_ID_SEQ")
	@Id
	@Column(name="SOURCE_CFG_LINE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	private int sourceConfiguratorLineId;
	
	@Column(name="SOURCE_CFG_ID")
	private int sourceConfiguratorId;
	
	@Column(name="SOURCE_CFG_COLUMN_NAME")
	private String columnName;
	
	@Column(name="SOURCE_DATATYPE")
	private String dataType;
	
	@Column(name="SOURCE_MANDATORY_COLUMN")
	private String columnMandatoryFlag;
	
	@Column(name="SOURCE_UNIQUE_COLUMN")
	private String columnUniqueFlag;
	
	@Column(name="SOURCE_CONVERSION_COLUMN")
	private String conversionColumn;
	
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
}
