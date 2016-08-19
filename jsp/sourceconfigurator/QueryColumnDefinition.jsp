<%@ taglib uri="struts-html" prefix="html"%><%@ taglib uri="struts-bean" prefix="bean"%><%@ taglib uri="struts-nested" prefix="nested"%>
<html:html>
<head>
<link href="css/basepagemaker.css" rel='stylesheet' type='text/css' />
</head>
<nested:root name="queryDefinitionForm">
	<html:form action="/QueryDefinitionSaveAction.etl">
		<nested:nest property="queryDefinitionVO">
			<div id="pageContentDivId">
				<div class="div-table">
					<div class="div-table-row">
						<div class="div-table-col">
							<span> Source Connection </span>
						</div>
						<div class="div-table-col">
							<nested:text styleId="sourceConfigConnection"
								property="sourceConfigConnection" />
							<nested:hidden styleId="sourceConfigConnectionId"
								property="sourceConfigConnectionId" />
						</div>
						<div class="div-table-col">
							<span> Source Configure Name </span>
						</div>
						<div class="div-table-col">
							<nested:text styleId="sourceConfigName"
								property="sourceConfigName" />
							<nested:hidden styleId="sourceConfigNameId"
								property="sourceConfigNameId" />
							<nested:hidden styleId="saveFlag" property="saveFlag" />
						</div>
					</div>
				</div>
				<table cellpadding="0" cellspacing="0" border="0" width="95%" class="cs-sap-table">
        		 <thead>
            		<tr>
		      			<th>Column Name</th>
		               	<th>Data type</th>
		                <th>Mandatory Column</th>
		                <th>Unique Column</th>
		                <th>Conversion Column</th>
		      		</tr>
            	 </thead>
               		<nested:present property="queryDefinitionLineVOList">
               			<nested:iterate property="queryDefinitionLineVOList" id="queryDefinitionLineVOList" 
               				type="com.dataprocess.bods.vo.QueryDefinitionLineVO" indexId="count">
                			<tr>
                				<td>
                					<nested:write property="columnName"/>
                					<nested:hidden property="sourceConfiguratorLineId" styleId="<%= \"sourceConfiguratorLineId\" + count%>"/>
                				</td>
                				<td>
                					<nested:write property="dataType"/>
                				</td>
                				<td align="left" style="padding-left: 50px;">
                					<nested:equal property="columnMandatoryFlag" value="Y">
				              			<input type="checkbox" checked onclick="<%= "if(this.checked){document.forms[0].columnMandatoryFlag" + count + ".value='Y'; }else{document.forms[0].columnMandatoryFlag" + count + ".value='N';}"%>"/>
				              		</nested:equal>
				              		<nested:notEqual property="columnMandatoryFlag" value="Y">
				              			<input type="checkbox" onclick="<%= "if(this.checked){document.forms[0].columnMandatoryFlag" + count + ".value='Y'; }else{document.forms[0].columnMandatoryFlag" + count + ".value='N';}"%>"/>
				              		</nested:notEqual>
				              		<nested:hidden property="columnMandatoryFlag" styleId="<%= \"columnMandatoryFlag\" + count%>"/>
                				</td>
                				<td align="left" style="padding-left: 50px;">
                					<nested:equal property="columnUniqueFlag" value="Y">
				              			<input type="checkbox" checked onclick="<%= "if(this.checked){document.forms[0].columnUniqueFlag" + count + ".value='Y'; }else{document.forms[0].columnUniqueFlag" + count + ".value='N';}"%>"/>
				              		</nested:equal>
				              		<nested:notEqual property="columnUniqueFlag" value="Y">
				              			<input type="checkbox" onclick="<%= "if(this.checked){document.forms[0].columnUniqueFlag" + count + ".value='Y'; }else{document.forms[0].columnUniqueFlag" + count + ".value='N';}"%>"/>
				              		</nested:notEqual>
				              		<nested:hidden property="columnUniqueFlag" styleId="<%= \"columnUniqueFlag\" + count %>"/>
                				</td>
                				<td align="left" style="padding-left: 50px;">
                					<nested:equal property="conversionColumn" value="Y">
				              			<input type="checkbox" checked onclick="<%= "if(this.checked){document.forms[0].conversionColumn" + count + ".value='Y'; }else{document.forms[0].conversionColumn" + count + ".value='N';}"%>"/>
				              		</nested:equal>
				              		<nested:notEqual property="conversionColumn" value="Y">
				              			<input type="checkbox" onclick="<%= "if(this.checked){document.forms[0].conversionColumn" + count + ".value='Y'; }else{document.forms[0].conversionColumn" + count + ".value='N';}"%>"/>
				              		</nested:notEqual>
				              		<nested:hidden property="conversionColumn" styleId="<%= \"conversionColumn\" + count %>"/>
                				</td>
                			</tr>
               			</nested:iterate>
               		</nested:present>
            	</table>
			</div>
			<center>
				<button class="btn waves-effect waves-light" type="submit" name="action">Save</button>
				<a class="btn disabled" onclick="doNextProcess()">Show Records</a>&nbsp;&nbsp;&nbsp;
			</center>
		</nested:nest>
	</html:form>
</nested:root>
</html:html>
<script>
	if (document.getElementById('saveFlag').value == 'Y') {
		$("a").removeClass("disabled");
	}
</script>