<%@ page language="java"%><%@ taglib uri="struts-html" prefix="html"%><%@ taglib uri="struts-bean" prefix="bean"%><%@ taglib uri="struts-nested" prefix="nested"%>

<html:html>
	<head>
		<link href="css/basepagemaker.css" rel='stylesheet' type='text/css' />
	</head>
	<script>
	function callExecute() {
		document.configuratorForm.action='/bods/ConfiguratorExecuteMapping.etl?configuratorId=14&configuratorConnId=2';
		document.configuratorForm.submit();
	}
</script>
<nested:root name="configuratorForm">
	<html:form styleId="ldrForm" action="ConfiguratorColumnDefinition">
		<nested:nest property="configuratorVO">
			<nested:define id="configConnectionListId"
				property="configConnectionList" />
			<div id="pageContentDivId">
				<div class="div-table">
					<div class="div-table-row">
						<div class="div-table-col">
							<span> Connection </span>
						</div>
						<div class="div-table-col">
						
						<nested:select styleId="configConn"
								property="configuratorConnectionId">
								<html:option value="">--Select--</html:option>
								<html:options collection="configConnectionListId"
									property="configuratorConnectionId"
									labelProperty="configuratorConnectionName" />
							</nested:select>
							<nested:hidden styleId="configuratorConnectionName"
								property="configuratorConnectionName" />
						
						
							<%-- <nested:text styleId="configuratorConnectionName"
								property="configuratorConnectionName" />
							<nested:hidden styleId="configuratorConnectionId"
								property="configuratorConnectionId" /> --%>
						</div>
						<div class="div-table-col">
							<span> Description </span>
						</div>
						<div class="div-table-col">
							<nested:text styleId="description" property="description" />
						</div>
					</div>

					<div class="div-table-row">
						<div class="div-table-col">
							<span> Name </span>
						</div>
						<div class="div-table-col">
							<nested:text styleId="configuratorName"
								property="configuratorName" />
							<nested:hidden styleId="configuratorId" property="configuratorId" />
						</div>
						<div class="div-table-col">
							<span> Display Name </span>
						</div>
						<div class="div-table-col">
							<nested:text styleId="displayName" property="displayName" />
						</div>
					</div>

					<div class="div-table-row">
						<div class="div-table-col">
							<input type="checkbox" name="redundantReducer"
								<nested:equal property="redundantReducer" value="Y">checked="checked"</nested:equal>
								onclick="<%="if(this.checked){document.forms[0].redundantRecuderFlag.value='Y';}else{document.forms[0].redundantRecuderFlag.value='N'; }"%>">
							<nested:hidden property="redundantReducer"
								styleId="redundantRecuderFlag" />
							<span> Redundancy Reducer </span>
						</div>	
						<div class="div-table-col">
							<input type="checkbox" name="conversion"
								<nested:equal property="conversion" value="Y">checked="checked"</nested:equal>
								onclick="<%="if(this.checked){document.forms[0].conversionFlag.value='Y';}else{document.forms[0].conversionFlag.value='N'; }"%>">
							<nested:hidden property="conversion" styleId="conversionFlag" />
							<span> Data Conversion </span>
						</div>
					</div>
					<br/>
					<center>
						<button class="btn waves-effect waves-light" onclick="callExecute()" name="action">Execute</button>
						<button class="btn waves-effect waves-light" type="submit" name="action">Next</button>
					</center>
				</div>
			</div>
		</nested:nest>
	</html:form>
</nested:root>
<script>
	$('#configConn').change(function() {
		/* alert('text :' + $('#sourceConfigConn option:selected').html()); */
		$('#configuratorConnectionName').val($('#configConn option:selected').html());
	})
</script>
</html:html>
