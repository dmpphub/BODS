<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<script>
	function doNextProcess() {
		location.href = "/bods/QueryDefinitionColumnList.etl";
	}
</script>
<style>
.div-table {
	display: table;
	width: 100%;
	border-spacing: 5px;
}

.div-table-row {
	display: table-row;
	width: auto;
	clear: both;
}

.div-table-col {
	float: left;
	display: table-column;
	width: auto;
	line-height: 25px;
	align: middle;
	margin: 10px;
}

.div-table-col span {
	float: left;
	width: 220px;
	padding-left: 34px;
}

input[type="text"], input[type="Password"], select {
	width: 200px;
	-ms-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	-webkit-box-sizing: border-box;
	height: 25px;
}

#pageContentDivId {
	width: 100%;
	height: 410px;
	background-color: #eee;
}
</style>
<html:html>
<html:form action="/QueryDefinitionValidate.etl">
	<nested:root name="queryDefinitionForm">
		<nested:nest property="queryDefinitionVO">
			<nested:define id="sourceConfigConnectionListId"
				property="sourceConfigConnectionList" />
			<div id="pageContentDivId">
				<div class="div-table">
					<div class="div-table-row">
						<div class="div-table-col">
							<span> Connection </span>
						</div>
						<div class="div-table-col">
							<nested:select styleId="sourceConfigConn"
								property="sourceConfigConnectionId">
								<html:option value="">--Select--</html:option>
								<html:options collection="sourceConfigConnectionListId"
									property="sourceConfigConnectionId"
									labelProperty="sourceConfigConnection" />
							</nested:select>
							<nested:hidden styleId="sourceConfigConnection"
								property="sourceConfigConnection" />
						</div>
						<div class="div-table-col">
							<span> Configurator Name </span>
						</div>
						<div class="div-table-col">
							<nested:text styleId="sourceConfigName"
								property="sourceConfigName" />
							<nested:hidden styleId="sourceConfigNameId"
								property="sourceConfigNameId" />
						</div>
					</div>

					<div class="div-table-row">
						<div class="div-table-col">
							<span> Display Name </span>
						</div>
						<div class="div-table-col">
							<nested:text styleId="displayName" property="displayName" />
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
							<span> Query </span>
						</div>
						<div class="div-table-col">
							<nested:textarea styleId="sqlQuery" property="sqlQuery" rows="7"
								cols="60" style="resize:none;" />
							<nested:hidden styleId="queryValidatedFlag"
								property="queryValidatedFlag" />
						</div>
					</div>
					<br />
					<center>
						<button class="btn waves-effect waves-light" type="submit"
							name="action">Parse</button>
						<a class="btn disabled" onclick="doNextProcess()">Next</a>&nbsp;&nbsp;&nbsp;
					</center>
				</div>
			</div>
		</nested:nest>
	</nested:root>
</html:form>
</html:html>
<script>
	$('#pagination').html("<a href='/bods/QueryDefinitionLaunch.etl'>Source Configurator</a>");
	if (document.getElementById('queryValidatedFlag').value == 'Y') {
		$("a").removeClass("disabled");
	}
	
	$('#sourceConfigConn').change(function() {
		/* alert('text :' + $('#sourceConfigConn option:selected').html()); */
		$('#sourceConfigConnection').val($('#sourceConfigConn option:selected').html());
	})
</script>