package com.dataprocess.bods.vo;

import java.io.Serializable;

/**
 * The Class ExtractConfigurationVO.
 */
public class ExtractConfigurationVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4333555395707832496L;

	/** The extract id. */
	private int extractId;

	/** The extract name. */
	private String extractName;

	/** The display name. */
	private String displayName;

	/** The description. */
	private String description;

	/** The query. */
	private String query;

	/** The extract column vo. */
	private ExtractColumnVO extractColumnVO;

	/**
	 * Gets the extract id.
	 *
	 * @return the extractId
	 */
	public int getExtractId() {
		return extractId;
	}

	/**
	 * Sets the extract id.
	 *
	 * @param extractId the extractId to set
	 */
	public void setExtractId(int extractId) {
		this.extractId = extractId;
	}

	/**
	 * Gets the extract name.
	 *
	 * @return the extractName
	 */
	public String getExtractName() {
		return extractName;
	}

	/**
	 * Sets the extract name.
	 *
	 * @param extractName the extractName to set
	 */
	public void setExtractName(String extractName) {
		this.extractName = extractName;
	}

	/**
	 * Gets the display name.
	 *
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 *
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Sets the query.
	 *
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Gets the extract column vo.
	 *
	 * @return the extractColumnVO
	 */
	public ExtractColumnVO getExtractColumnVO() {
		return extractColumnVO;
	}

	/**
	 * Sets the extract column vo.
	 *
	 * @param extractColumnVO the extractColumnVO to set
	 */
	public void setExtractColumnVO(ExtractColumnVO extractColumnVO) {
		this.extractColumnVO = extractColumnVO;
	}

}
