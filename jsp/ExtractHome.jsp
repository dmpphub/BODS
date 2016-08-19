<!doctype html>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ include file="/jsp/components/ETLLoadComponents.jsp" %>
<%@ include file="/jsp/components/NavigationMenu.jsp" %>
<html lang="en" class="no-js">
<script>
	function callValidateQuery() {
		
	}
</script>
<head>
	<title>Extract</title>
</head>
<body>
	<html:form action="/ExtractionManualQueryAction" method="post">
		<main class="cd-main">
			<section class="cd-section ExtractForm visible">
				<header>
					<br>
					<center>
						<div style="font-size: 250%;color: white;">Title</div>
					</center>
				</header>	
					<div class="cd-content" id="extract-content">
					<table border="1" align="center">
						<tr align="center">
							<td width="20%">
								<label>Instance Name</label>
							</td>
							<td  width="20%">
								<html:text property="instanceName" name="ExtractForm"  />
								<html:hidden property="instanceId"/>
							</td>
							<td width="20%">
								<label>Description</label>
							</td>
							<td  width="20%">
								<html:textarea cols="3" rows="3" property="instanceDescription" name="ExtractForm"  />
							</td>
						</tr>
						<tr align="center">
							<td width="20%">
								<label>Extract Name</label>
							</td>
							<td width="20%">
								<html:text property="extractName" name="ExtractForm"  />
								<html:hidden property="extractId"/>
							</td>
						</tr>
						<tr align="center">
							<td width="20%">
								<label>Query Definition</label>
							</td>
							<td colspan="2">
								<html:textarea cols="6" rows="6" property="extractQueryDescription" name="ExtractForm" />
							</td>
						</tr>
						<tr align="center">
							<td>
								<a href="javascript:;" class="btn" outline onclick="callValidateQuery()">New</a>
							</td>
							<td>
								<a href="javascript:;" class="btn"  type="primary" onclick="callValidateQuery()">Run Query</a>
							</td>
						</tr>
					</table>
				</div>
			</section>
		</main>	
	</html:form>
	<div id="cd-loading-bar" data-scale="1" class="ExtractForm"></div>
	<script src="js/jquery-2.1.4.js"></script>
	<script src="js/velocity.min.js"></script>
	<script src="js/main.js"></script>
</body>
</html>