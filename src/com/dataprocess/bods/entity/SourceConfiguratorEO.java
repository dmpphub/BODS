package com.dataprocess.bods.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The Class SourceConfiguratorEO.
 */
@Entity
@Table(name = "BODS_SOURCE_CFG")
public class SourceConfiguratorEO {

	/** The source configurator id. */
	@SequenceGenerator(name = "generator", sequenceName = "SOURCE_CFG_ID_SEQ")
	@Id
	@Column(name = "SOURCE_CFG_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	private int sourceConfiguratorId;

	/** The source config connection id. */
	@Column(name = "CONNECTION_ID")
	private int sourceConfigConnectionId;

	/** The source config name. */
	@Column(name = "SOURCE_CFG_NAME")
	private String sourceConfigName;

	/** The display name. */
	@Column(name = "DISPLAY_NAME")
	private String displayName;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The sql query. */
	@Column(name = "SQL_QUERY")
	private String sqlQuery;

	/** The source configurator line eo set. */
	@JoinColumn(name = "SOURCE_CFG_ID")
	@OneToMany
	private Set<SourceConfiguratorLineEO> sourceConfiguratorLineEOSet;

}
