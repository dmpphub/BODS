package com.dataprocess.bods.business;

import java.util.ArrayList;
import java.util.List;

import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.vo.ConfiguratorColumnDefinitionVO;
import com.dataprocess.bods.vo.ConfiguratorInterfaceColumnVO;
import com.dataprocess.bods.vo.ConfiguratorVO;
import com.dataprocess.bods.vo.ConfiguratorValidationVO;

/**
 * The Class ConfiguratorProcedureCreation.
 */
public final class ConfiguratorProcedureCreation {

    /**
     * Creates the staging procedure.
     * 
     * @param configuratorVO the configurator vo
     * @return the string
     * @throws BODSException the bODS exception
     */
    public void createStagingProcedure(ConfiguratorVO configuratorVO) throws BODSException {
        String procedureValue = "";
        StringBuffer procedureContent = null;
        try {
            procedureContent = new StringBuffer();
            variableDeclareBlock(procedureContent, configuratorVO);
            procedureValue = procedureContent.toString();
            configuratorVO.setProcedureValue(procedureValue);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorProcedureCreation", "getSourceConfigurator", exception.getMessage());
        }
    }

    /**
     * Creates the validation procedure.
     *
     * @param configuratorVO the configurator vo
     * @return the string
     * @throws BODSException the bODS exception
     */
    public String createValidationProcedure(ConfiguratorVO configuratorVO) throws BODSException {
        StringBuffer procedureContent = null;
        try {
            procedureContent = new StringBuffer();
            buildValidationMergeBlock(procedureContent, configuratorVO);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorProcedureCreation", "createValidationProcedure",
                exception.getMessage());
        }
        return procedureContent.toString();
    }

    /**
     * Builds the validation merge block.
     *
     * @param procedureContent the procedure content
     * @param configuratorVO the configurator vo
     * @throws BODSException the bODS exception
     */
    private void buildValidationMergeBlock(StringBuffer procedureContent, ConfiguratorVO configuratorVO)
        throws BODSException {
        int count = 0;
        List<String> prevalidationSeqNo = null;
        List<ConfiguratorValidationVO> preValidationVOList = null;
        try {
            prevalidationSeqNo = new ArrayList<String>();
            preValidationVOList = configuratorVO.getConfiguratorValidationVOList();
            if (preValidationVOList != null && preValidationVOList.size() > 0) {
                for (ConfiguratorValidationVO configuratorValidationVO : preValidationVOList) {
                    ++count;
                    procedureContent = new StringBuffer();
                    prevalidationSeqNo.add("PV_" + count);
                    procedureContent.append(" DECLARE \n");
                    procedureContent.append(" L_MERGE_ERR_MSG VARCHAR2(4000); \n");
                    procedureContent.append(" L_ERROR_COUNT   NUMBER; \n");
                    procedureContent.append(" L_SUCCESS_COUNT NUMBER; \n");
                    procedureContent.append(" L_END_TIME TIMESTAMP; \n");
                    procedureContent.append(" BEGIN \n");
                    procedureContent.append(" BEGIN \n");
                    procedureContent.append(" UPDATE ##PV_STAGING_TABLE## SET START_TIME = ");
                    procedureContent.append(" (SELECT TO_CHAR(SYSTIMESTAMP, 'DD-MON-YYYY HH:MI:SS.FF AM') FROM DUAL)");
                    procedureContent.append(" WHERE PV_SEQ_ORDER_NO = '##PV_COLUMN_SEQNO##' AND START_TIME IS NULL ;");
                    procedureContent.append(" BEGIN \n");
                    procedureContent.append(" MERGE INTO ##STAGING_TABLE## STGTBL USING \n");
                    procedureContent.append(" (" + configuratorValidationVO.getValidationQuery() + ")\n");
                    procedureContent.append(" PVQUERY ON ( " + configuratorValidationVO.getValidationConditionString()
                        + " AND ( DC_FLAG IS NULL ) ");
                    procedureContent.append(" AND EXECUTION_ID = ##EXECUTION_ID##) \n");
                    procedureContent.append(" WHEN MATCHED THEN  \n");
                    if ("Success".equalsIgnoreCase(configuratorValidationVO.getValidationInference())) {
                        if (configuratorValidationVO.getValidationConversion() != null
                            && !configuratorValidationVO.getValidationConversion().equals("")) {
                            procedureContent.append(" UPDATE SET ##PV_COLUMN_SEQNO## = 'S', "
                                + configuratorValidationVO.getValidationConversion() + "; \n");
                        } else {
                            procedureContent.append(" UPDATE SET ##PV_COLUMN_SEQNO## = 'S' ; \n");
                        }
                    } else {
                        if (configuratorValidationVO.getValidationConversion() != null
                            && !configuratorValidationVO.getValidationConversion().equals("")) {
                            procedureContent.append(" UPDATE SET ##PV_COLUMN_SEQNO## = 'E', "
                                + configuratorValidationVO.getValidationConversion() + "; \n");
                        } else {
                            procedureContent.append("UPDATE SET ##PV_COLUMN_SEQNO## = 'E' ; \n");
                        }
                    }
                    procedureContent.append(" L_SUCCESS_COUNT := SQL%ROWCOUNT; \n");
                    procedureContent.append(" EXCEPTION \n");
                    procedureContent.append(" WHEN OTHERS THEN ");
                    procedureContent.append(" L_MERGE_ERR_MSG := SQLERRM; \n");
                    procedureContent.append(" UPDATE ##STAGING_TABLE## SET ##PV_COLUMN_SEQNO## = 'E' \n");
                    procedureContent.append(" WHERE EXECUTION_ID = ##EXECUTION_ID## ;");
                    procedureContent.append(" END;\n");
                    procedureContent.append(" L_ERROR_COUNT := ##TOTAL_COUNT## - L_SUCCESS_COUNT; ");
                    procedureContent.append(" UPDATE ##PV_STAGING_TABLE## \n");
                    procedureContent.append(" SET SUCCESS_COUNT = NVL(SUCCESS_COUNT,0) + L_SUCCESS_COUNT, \n");
                    procedureContent.append(" END_TIME = (SELECT TO_CHAR(SYSDATE, 'DD-MON-YYYY HH:MI:SS AM') \n");
                    procedureContent.append(" FROM DUAL),ERROR_MESSAGE = SUBSTR (L_MERGE_ERR_MSG , 1, 400), \n");
                    procedureContent
                        .append(" ERROR_COUNT = CASE WHEN (ERROR_COUNT = 0 OR ERROR_COUNT IS NULL) THEN L_ERROR_COUNT \n");
                    procedureContent.append(" ELSE NVL(ERROR_COUNT,0) - L_SUCCESS_COUNT  END \n");
                    procedureContent
                        .append(" WHERE PV_SEQ_ORDER_NO = '##PV_COLUMN_SEQNO##' AND EXECUTION_ID = ##EXECUTION_ID## ;\n");
                    procedureContent.append(" EXCEPTION WHEN OTHERS THEN L_MERGE_ERR_MSG := SQLERRM;");
                    procedureContent
                        .append(" UPDATE ##STAGING_TABLE## SET STATUS_CODE = 'E',##PV_COLUMN_SEQNO## = SUBSTR(L_MERGE_ERR_MSG, 1, 200); ");
                    procedureContent.append(" END;");
                    procedureContent.append(" UPDATE ##PV_STAGING_TABLE## SET END_TIME = \n");
                    procedureContent
                        .append(" (SELECT TO_CHAR(SYSTIMESTAMP, 'DD-MON-YYYY HH:MI:SS.FF AM') FROM DUAL) \n");
                    procedureContent.append(" WHERE PV_SEQ_ORDER_NO = '##PV_COLUMN_SEQNO##' AND END_TIME IS NULL ;");
                    procedureContent.append(" COMMIT;");
                    procedureContent.append(" END;");
                    configuratorValidationVO.setValidationProcedureValue(procedureContent.toString());
                }
            }
            configuratorVO.setPrevalidationSeqNo(prevalidationSeqNo);
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorProcedureCreation", "buildValidationMergeBlock",
                exception.getMessage());
        }
    }

    /**
     * Variable declare block.
     * 
     * @param procedureContent the procedure content
     * @param configuratorVO the configurator vo
     * @throws BODSException the bODS exception
     */
    private void variableDeclareBlock(StringBuffer procedureContent, ConfiguratorVO configuratorVO)
        throws BODSException {
        String stagingTable = "";
        List<ConfiguratorInterfaceColumnVO> configuratorInterfaceColumnVOList = null;
        List<ConfiguratorColumnDefinitionVO> configuratorColumnDefinitionVOList = null;
        try {
            stagingTable =
                "STG_" + configuratorVO.getConfiguratorConnectionId() + "_" + configuratorVO.getConfiguratorId();
            configuratorColumnDefinitionVOList = configuratorVO.getConfiguratorColumnDefinitionVOList();
            configuratorInterfaceColumnVOList = configuratorVO.getConfiguratorInterfaceColumnVOList();
            procedureContent.append(" DECLARE \n");
            for (ConfiguratorColumnDefinitionVO configuratorColumnDefinitionVO : configuratorColumnDefinitionVOList) {
                if ("String".equalsIgnoreCase(configuratorColumnDefinitionVO.getDataType())) {
                    procedureContent.append(configuratorColumnDefinitionVO.getColumnName() + "_L"
                        + " VARCHAR2(2000); \n");
                } else {
                    procedureContent.append(configuratorColumnDefinitionVO.getColumnName() + "_L "
                        + configuratorColumnDefinitionVO.getDataType() + "; \n");
                }
            }
            buildProcedureBlock(stagingTable, procedureContent, configuratorColumnDefinitionVOList,
                configuratorInterfaceColumnVOList);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorProcedureCreation", "variableDeclareBlock", exception.getMessage());
        }
    }

    /**
     * Builds the procedure block.
     * 
     * @param stagingTable the staging table
     * @param procedureContent the procedure content
     * @param configuratorColumnDefinitionVOList the configurator column definition vo list
     * @throws BODSException the bODS exception
     */
    private void buildProcedureBlock(String stagingTable, StringBuffer procedureContent,
        List<ConfiguratorColumnDefinitionVO> configuratorColumnDefinitionVOList,
        List<ConfiguratorInterfaceColumnVO> configuratorInterfaceColumnVOList) throws BODSException {
        int index = 0;
        int curIndex = 0;
        String interfaceTableName = "";
        String columnName = "";
        String columnValue = "";
        try {
            procedureContent.append(" CURSOR BODS \n");
            procedureContent.append(" IS \n");
            for (ConfiguratorColumnDefinitionVO configuratorColumnDefinitionVO : configuratorColumnDefinitionVOList) {
                if (index == 0) {
                    procedureContent.append(" SELECT BODS." + configuratorColumnDefinitionVO.getColumnName() + "\n");
                } else {
                    procedureContent.append(" , BODS." + configuratorColumnDefinitionVO.getColumnName() + "\n");
                }
                index++;
            }
            procedureContent.append(" FROM ##STAGING_TABLE## BODS ; \n");
            procedureContent.append(" BEGIN \n");
            procedureContent.append(" FOR CUR_BODS IN BODS \n");
            procedureContent.append(" LOOP \n");
            for (ConfiguratorInterfaceColumnVO configuratorInterfaceColumnVO : configuratorInterfaceColumnVOList) {
                if (configuratorInterfaceColumnVO != null
                    && configuratorInterfaceColumnVO.getMappedColumnFlag() != null
                    && "Y".equalsIgnoreCase(configuratorInterfaceColumnVO.getMappedColumnFlag())) {
                    if (curIndex == 0) {
                        interfaceTableName = configuratorInterfaceColumnVO.getTableName();
                        columnName += configuratorInterfaceColumnVO.getColumnName();
                        columnValue += configuratorInterfaceColumnVO.getAttributeName() + "_L";
                    } else {
                        columnName += ", " + configuratorInterfaceColumnVO.getColumnName();
                        columnValue += ", " + configuratorInterfaceColumnVO.getAttributeName() + "_L";
                    }
                    procedureContent.append(configuratorInterfaceColumnVO.getAttributeName() + "_L := CUR_BODS."
                        + configuratorInterfaceColumnVO.getAttributeName() + "; \n");
                    curIndex++;
                }
            }
            procedureContent.append(" BEGIN \n");
            procedureContent.append(" INSERT INTO \n");
            procedureContent.append(interfaceTableName + " (" + columnName + ") \n");
            procedureContent.append(" VALUES (" + columnValue + "); \n");
            procedureContent.append(" COMMIT ; \n");
            procedureContent.append(" END ; \n");
            procedureContent.append(" END LOOP; \n");
            procedureContent.append(" END ; \n");
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorProcedureCreation", "cursorDeclareBlock", exception.getMessage());
        }
    }
}
