package com.dataprocess.bods.web.form;

import org.apache.struts.action.ActionForm;

import com.dataprocess.bods.vo.QueryDefinitionVO;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryDefinitionForm.
 */
public class QueryDefinitionForm extends ActionForm {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1894016333535176850L;

    /** The query definition vo. */
    private QueryDefinitionVO queryDefinitionVO;

    /**
     * Instantiates a new query definition form.
     */
    public QueryDefinitionForm() {
        setQueryDefinitionVO(new QueryDefinitionVO());
    }

    /**
     * Gets the query definition vo.
     *
     * @return the query definition vo
     */
    public QueryDefinitionVO getQueryDefinitionVO() {
        return queryDefinitionVO;
    }

    /**
     * Sets the query definition vo.
     *
     * @param queryDefinitionVO the new query definition vo
     */
    public void setQueryDefinitionVO(QueryDefinitionVO queryDefinitionVO) {
        this.queryDefinitionVO = queryDefinitionVO;
    }
}
