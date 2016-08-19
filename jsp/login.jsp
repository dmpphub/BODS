<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<script src="js/materialize.js"></script>
<link href="css/materialize.min.css" rel='stylesheet' type='text/css' />
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<style>
#loginContainer {
	position: relative;
	width: 900px;
	height: 100px;
	padding: 20px;
	position: absolute;
	top: 40%;
	left: 45%;
	margin: -100px 0 0 -170px;
}
</style>
<script>
	function callLogin() {
		document.forms[0].action = '/bods/LoginAction.etl';
		document.forms[0].submit();
	}
</script>
<html>
	<html:form action="/LoginAction.etl">
		<nested:root name="loginForm">
			<nested:nest property="loginVO">
				<div id="loginContainer">
					<div class="row">
	        			<div class="input-field col s4"> 
							<i class="material-icons prefix">account_circle</i>
							<nested:text styleId="userName" property="userName" />
						</div>
					</div>
					<div class="row">	
						<div class="input-field col s4">
							<i class="material-icons prefix">visibility_off</i>
							<nested:password styleId="password" property="password" />
						</div>
					</div>
					<div class="row">
						<div class="input-field col s4">
							<button class="btn waves-effect waves-light" type="submit" name="action">Login</button>
							<a class="btn" onclick="doNextProcess()">Clear</a>&nbsp;&nbsp;&nbsp;
						</div>
					</div>
				</div>	
			</nested:nest>
		</nested:root>
	</html:form>
</html>