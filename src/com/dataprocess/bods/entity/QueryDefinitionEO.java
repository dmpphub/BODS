package com.dataprocess.bods.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "BODS_SOURCE_CFG")
public class QueryDefinitionEO {

	@SequenceGenerator(name = "generator", sequenceName = "SOURCE_CFG_ID_SEQ")
	@Id
	@Column(name = "SOURCE_CFG_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	private int sourceConfiguratorId;

	@Column(name = "CONNECTION_ID")
	private int sourceConfigConnectionId;

	@Column(name = "SOURCE_CFG_NAME")
	private String sourceConfigName;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "SQL_QUERY")
	private String sqlQuery;

	@JoinColumn(name = "SOURCE_CFG_ID")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private Set<QueryDefinitionLineEO> sourceConfiguratorLineEOSet;

	public int getSourceConfiguratorId() {
		return sourceConfiguratorId;
	}

	public void setSourceConfiguratorId(int sourceConfiguratorId) {
		this.sourceConfiguratorId = sourceConfiguratorId;
	}

	public int getSourceConfigConnectionId() {
		return sourceConfigConnectionId;
	}

	public void setSourceConfigConnectionId(int sourceConfigConnectionId) {
		this.sourceConfigConnectionId = sourceConfigConnectionId;
	}

	public String getSourceConfigName() {
		return sourceConfigName;
	}

	public void setSourceConfigName(String sourceConfigName) {
		this.sourceConfigName = sourceConfigName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public Set<QueryDefinitionLineEO> getSourceConfiguratorLineEOSet() {
		return sourceConfiguratorLineEOSet;
	}

	public void setSourceConfiguratorLineEOSet(Set<QueryDefinitionLineEO> sourceConfiguratorLineEOSet) {
		this.sourceConfiguratorLineEOSet = sourceConfiguratorLineEOSet;
	}

}
