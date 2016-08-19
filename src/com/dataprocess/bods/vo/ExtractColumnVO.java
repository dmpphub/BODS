package com.dataprocess.bods.vo;

import java.io.Serializable;

/**
 * The Class ExtractColumnVO.
 */
public class ExtractColumnVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7529415411218403272L;

	/** The extract id. */
	private int extractId;

	/** The extract column id. */
	private int extractColumnId;

	/** The extract column name. */
	private String extractColumnName;

	/** The extract table name. */
	private String extractTableName;

	/**
	 * Gets the extract id.
	 *
	 * @return the extract id
	 */
	public int getExtractId() {
		return extractId;
	}

	/**
	 * Sets the extract id.
	 *
	 * @param extractId the new extract id
	 */
	public void setExtractId(int extractId) {
		this.extractId = extractId;
	}

	/**
	 * Gets the extract column id.
	 *
	 * @return the extract column id
	 */
	public int getExtractColumnId() {
		return extractColumnId;
	}

	/**
	 * Sets the extract column id.
	 *
	 * @param extractColumnId the new extract column id
	 */
	public void setExtractColumnId(int extractColumnId) {
		this.extractColumnId = extractColumnId;
	}

	/**
	 * Gets the extract column name.
	 *
	 * @return the extract column name
	 */
	public String getExtractColumnName() {
		return extractColumnName;
	}

	/**
	 * Sets the extract column name.
	 *
	 * @param extractColumnName the new extract column name
	 */
	public void setExtractColumnName(String extractColumnName) {
		this.extractColumnName = extractColumnName;
	}

	/**
	 * Gets the extract table name.
	 *
	 * @return the extract table name
	 */
	public String getExtractTableName() {
		return extractTableName;
	}

	/**
	 * Sets the extract table name.
	 *
	 * @param extractTableName the new extract table name
	 */
	public void setExtractTableName(String extractTableName) {
		this.extractTableName = extractTableName;
	}

}
