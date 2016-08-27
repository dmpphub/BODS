<%@ page language="java"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<html:html>
	<script src="js/pie-chart.js" type="text/javascript"></script>
	<script src="js/prevalidationprocess.js" type="text/javascript"></script>
	<script src="js/jquery.nicescroll.js"></script>
	<head>
		<link href="css/basepagemaker.css" rel='stylesheet' type='text/css' />
		<link href="css/prevalidationprocess.css" rel='stylesheet' type="text/css" />
	</head>
	<script>
		function callExecute() {
			document.configuratorForm.action='/bods/ConfiguratorFinalExecuteMapping.etl';
			document.configuratorForm.submit();
		}
	</script>
	<nested:root name="configuratorForm">
	<html:form>
		<nested:nest property="configuratorVO">
		<body width="100%">
				<div id="pageContentDivId" style="background-color: #fff;">
					<div style="width: 70%;" id="inner-content-div">
						<!-- <div class="div-table-row-progress">
						<div class="div-table-col" style="width=50px;">
						<table>
							<tr>
								<th>EMPLOYEEIDS COLUMN</th>
							</tr>
							<tr>
								<td>
								   The validation checks the unique column
								</td>
							</tr>
						</table>
					</div>
					<div class="div-table-col">
						<div  class="pie-title-center success" data-percent="75" style="margin-left: 100px;margin-top: 2px;"> <span style="margin-left: -100px;margin-top: -23px;" class="pie-value"></span> </div>
					</div>
					<div class="div-table-col">
						<div  class="pie-title-center error" data-percent="25" style="margin-top: 2px;"> <span style="margin-left: -100px;margin-top: -23px;" class="pie-value"></span> </div>
					</div>
				</div> -->
				</div>
					<div style="margin-left: 680px;margin-top: -355px;width: 15px;">
						<p>
							<img style="margin-left: 100px;" alt="No Image" height="200px" src="data:image/png;base64,<nested:write property="prevalDataString" />"/>
						</p>
					</div>
				</div>
				<center><br/>
					<button class="btn waves-effect waves-light" type="button" onclick="callExecute()">Execute Configurator</button>
				</center>
		</body>
		</nested:nest>
	</html:form>
	</nested:root>
</html:html>