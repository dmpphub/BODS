package com.dataprocess.bods.web.form;

import org.apache.struts.action.ActionForm;

import com.dataprocess.bods.vo.QueryDefinitionVO;

public class QueryDefinitionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1894016333535176850L;
	
	private QueryDefinitionVO queryDefinitionVO;

	public QueryDefinitionForm() {
		setQueryDefinitionVO(new QueryDefinitionVO());
	}

	public QueryDefinitionVO getQueryDefinitionVO() {
		return queryDefinitionVO;
	}

	public void setQueryDefinitionVO(QueryDefinitionVO queryDefinitionVO) {
		this.queryDefinitionVO = queryDefinitionVO;
	}
}
