<%@ page language="java" %><%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>

<script>
	function callSubmit() {
		document.configuratorForm.action='/bods/ValidationLaunch.etl';
		document.configuratorForm.submit();
	}
</script>
<html:html>
<head>
	<link href="css/basepagemaker.css" rel='stylesheet' type='text/css' />
</head>
<nested:root name="configuratorForm">
	<html:form styleId="configuratorForm1">
		<nested:nest property="configuratorVO">
			<nested:define id="sourceConfigConnectionListId"
				property="sourceConfigConnectionList" />
			<nested:define id="sourceConfigNameListId"
				property="sourceConfigurationNameList" />	
			<div id="pageContentDivId">
				<div class="div-table">
					<div class="div-table-row">
						<div class="div-table-col">
							<span> Source Connection </span>
						</div>
						<div class="div-table-col">
							<nested:select styleId="sourceConfigConn"
								property="sourceConfigurationConnectionId">
								<html:option value="">--Select--</html:option>
								<html:options collection="sourceConfigConnectionListId"
									property="sourceConfigurationConnectionId"
									labelProperty="sourceConfigurationConnectionName" />
							</nested:select>
							<nested:hidden styleId="sourceConfigurationConnectionName"
								property="sourceConfigurationConnectionName" />
						</div>
						<div class="div-table-col">
							<span> Source Configure Name </span>
						</div>
						<div class="div-table-col">
							<nested:select styleId="sourceConfigName"
								property="sourceConfigurationId">
								<html:option value="">--Select--</html:option>
								<html:options collection="sourceConfigNameListId"
									property="sourceConfigurationId"
									labelProperty="sourceConfigurationName" />
							</nested:select>
							<nested:hidden styleId="sourceConfigurationName"
								property="sourceConfigurationName" />
							<nested:hidden styleId="description" property="description"/>
							<nested:hidden styleId="configuratorName" property="configuratorName"/>
							<nested:hidden styleId="configuratorId" property="configuratorId"/>
							<nested:hidden styleId="displayName" property="displayName"/>
							<nested:hidden styleId="redundantReducer" property="redundantReducer"/>
							<nested:hidden styleId="conversion" property="conversion"/>	
						</div>
						<div class="div-table-col">
							<button class="btn waves-effect waves-light" type="submit"
							name="action">Fetch</button>
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
		                <th>Active</th>
		      		</tr>
            	 </thead>
            	 <div class="" style="overflow: auto;">
        		 	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="cs-sap-table">
                		<tbody>
                		<nested:present property="configuratorColumnDefinitionVOList">
                			<nested:iterate property="configuratorColumnDefinitionVOList" id="configuratorColumnDefinitionVOList" 
                				type="com.dataprocess.bods.vo.ConfiguratorColumnDefinitionVO" indexId="count">
                			<tr>
                				<td>
                					<nested:write property="columnName"/>
                					<nested:hidden property="columnId" styleId="<%= \"columnId\" + count%>"/>
                				</td>
                				<td>
                					<nested:write property="dataType"/>
                				</td>
                				<td>
                					<nested:equal property="mandatory" value="Y">
				              			<input type="checkbox" checked onclick="<%= "if(this.checked){document.forms[0].mandatory" + count + ".value='Y'; }else{document.forms[0].mandatory" + count + ".value='N';}"%>"/>
				              		</nested:equal>
				              		<nested:notEqual property="mandatory" value="Y">
				              			<input type="checkbox" onclick="<%= "if(this.checked){document.forms[0].mandatory" + count + ".value='Y'; }else{document.forms[0].mandatory" + count + ".value='N';}"%>"/>
				              		</nested:notEqual>
				              		<nested:hidden property="mandatory" styleId="<%= \"mandatory\" + count%>"/>
                				</td>
                				<td>
                					<nested:equal property="unique" value="Y">
				              			<input type="checkbox" checked onclick="<%= "if(this.checked){document.forms[0].unique" + count + ".value='Y'; }else{document.forms[0].unique" + count + ".value='N';}"%>"/>
				              		</nested:equal>
				              		<nested:notEqual property="unique" value="Y">
				              			<input type="checkbox" onclick="<%= "if(this.checked){document.forms[0].unique" + count + ".value='Y'; }else{document.forms[0].unique" + count + ".value='N';}"%>"/>
				              		</nested:notEqual>
				              		<nested:hidden property="unique" styleId="<%= \"unique\" + count %>"/>
                				</td>
                				<td>
                					<nested:equal property="conversionColumn" value="Y">
				              			<input type="checkbox" checked onclick="<%= "if(this.checked){document.forms[0].conversionColumn" + count + ".value='Y'; }else{document.forms[0].conversionColumn" + count + ".value='N';}"%>"/>
				              		</nested:equal>
				              		<nested:notEqual property="conversionColumn" value="Y">
				              			<input type="checkbox" onclick="<%= "if(this.checked){document.forms[0].conversionColumn" + count + ".value='Y'; }else{document.forms[0].conversionColumn" + count + ".value='N';}"%>"/>
				              		</nested:notEqual>
				              		<nested:hidden property="conversionColumn" styleId="<%= \"conversionColumn\" + count %>"/>
                				</td>
                				<td>
                					<nested:equal property="activeColumnFlag" value="Y">
				              			<input type="checkbox" checked onclick="<%= "if(this.checked){document.forms[0].activeColumnFlag" + count + ".value='Y'; }else{document.forms[0].activeColumnFlag" + count + ".value='N';}"%>"/>
				              		</nested:equal>
				              		<nested:notEqual property="activeColumnFlag" value="Y">
				              			<input type="checkbox" onclick="<%= "if(this.checked){document.forms[0].activeColumnFlag" + count + ".value='Y'; }else{document.forms[0].activeColumnFlag" + count + ".value='N';}"%>"/>
				              		</nested:notEqual>
				              		<nested:hidden property="activeColumnFlag" styleId="<%= \"activeColumnFlag\" + count %>"/>
                				</td>
                			</tr>
                			</nested:iterate>
                		</nested:present>
                		</tbody>
                	</table>
                </div>
            	</table>
			</div>
		</nested:nest>
		<center>
			<button class="btn waves-effect waves-light" onclick="callSubmit()" name="action">Next</button>
		</center>
	</html:form>
</nested:root>
</html:html>
<script>
	$('#sourceConfigConn').change(function() {
		/* alert('text :' + $('#sourceConfigConn option:selected').html()); */
		$('#sourceConfigurationConnectionName').val($('#sourceConfigConn option:selected').html());
	})
	$('#sourceConfigName').change(function() {
		/* alert('text :' + $('#sourceConfigConn option:selected').html()); */
		$('#sourceConfigurationName').val($('#sourceConfigName option:selected').html());
	})
</script>