package com.dataprocess.bods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BODS_CONFIGURATOR_LINE_CFG")
public class ConfiguratorColumnDefinitionEO {

	@SequenceGenerator(name = "generator", sequenceName = "CONFIGURATOR_CFG_LINE_ID_SEQ")
	@Id
	@Column(name = "CONFIGURATOR_LINE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    private int columnId;

	@Column(name="CONFIGURATOR_ID")
	private int configuratorId;
	
	@Column(name = "CONFIGURATOR_COLUMN_NAME")
    private String columnName;

	@Column(name = "DATATYPE")
    private String dataType;

	@Column(name = "MANDATORY_COLUMN")
    private String mandatory;

	@Column(name = "UNIQUE_COLUMN")
    private String unique;

	@Column(name = "CONVERSION_COLUMN")
    private String conversionColumn;

	/*@Column(name = "ACTIVECOLUMNFLAG")
    private String activeColumnFlag;*/
	
	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public int getConfiguratorId() {
		return configuratorId;
	}

	public void setConfiguratorId(int configuratorId) {
		this.configuratorId = configuratorId;
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

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getConversionColumn() {
		return conversionColumn;
	}

	public void setConversionColumn(String conversionColumn) {
		this.conversionColumn = conversionColumn;
	}

	/*public String getActiveColumnFlag() {
		return activeColumnFlag;
	}

	public void setActiveColumnFlag(String activeColumnFlag) {
		this.activeColumnFlag = activeColumnFlag;
	}*/
}
