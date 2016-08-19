<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>

<html:html>
<head>
<link href="css/basepagemaker.css" rel='stylesheet' type='text/css' />
</head>
<nested:root name="configuratorForm">
	<html:form>
		<nested:nest property="configuratorVO">
			<nested:nest property="currentConfiguratorValidationVO">
				<div id="pageContentDivId">
					<div class="div-table">
						<div class="div-table-row">
							<div class="div-table-col">
								<span> Connection </span>
							</div>
							<div class="div-table-col">
								<nested:text styleId="concurrentProgramName"
									property="concurrentProgramName" />
							</div>
							<div class="div-table-col">
								<span> Description </span>
							</div>
							<div class="div-table-col">
								<nested:textarea styleId="description" property="description" />
							</div>
						</div>
					</div>
				</div>
			</nested:nest>
		</nested:nest>
	</html:form>
</nested:root>
</html:html>