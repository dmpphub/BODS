<!DOCTYPE>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<style>
#loginContainer {
	position: relative;
	width: 500px;
	height: 100px;
	padding: 20px;
	position: absolute;
	top: 40%;
	left: 45%;
	margin: -100px 0 0 -170px;
}
#loginMessage {
    position: relative;
	width: 500px;
	height: 100px;
	padding: 20px;
	position: absolute;
	top: 40%;
	left: 45%;
	margin: -100px 0 0 -170px;
}
</style>
<script>
	function callSubmit() {
		var validSubmit = true;		
		if (document.forms[0].userName.value.trim() == '') {
			document.getElementById('userNameError').style.display = 'block';
			userName.className = 'input-error';
			validSubmit = false;
		} else {
			document.getElementById('userNameError').style.display = 'none';
			userName.className = '';
		}
		if(document.forms[0].password.value.trim() == '') {
			document.getElementById('passwordError').style.display = 'block';
			password.className = 'input-error';
			validSubmit = false;
		} else {
			document.getElementById('passwordError').style.display = 'none';
			password.className = '';
		}
		if(validSubmit) {
			document.forms[0].submit();	
		}
	}
	function callClear() {
		document.forms[0].userName.value = '';
		document.getElementById('userNameError').style.display = 'none';
		userName.className = '';
		document.forms[0].password.value = '';
		document.getElementById('passwordError').style.display = 'none';
		password.className = '';
		document.getElementById('loginMessage').style.display = 'none';
	}
	function callCreateNewAccount() {
	}
</script>
<html>
<head>
	<title>Extract-Transform-Load</title>
</head>
	<body>
		<form method="post" action="LoginAction.etl" class="forms" name="loginForm">
			<br><br><br><br><br>
			<h3 style="text-align:center;font-weight: bold;color:#595959;">Login</h3>
			<div id="loginContainer">
				<fieldset>
	        		<legend>Login data</legend>
				    <section>
				        <label>User Name<span class="error" id="userNameError" style="display:none;">Please enter user name</span></label>
				        <html:text name="loginForm" property="userName" styleId="userName" />
				        <html:hidden name="loginForm" property="loginFlag" />
				    </section>
				    <section>
				        <label>Password<span class="error" id="passwordError" style="display:none;">Please enter password</span></label>
				        <html:password name="loginForm" property="password" styleId="password" />
				    </section>
				    <section>
				        <a href="javascript:;" class="btn" type="primary" onclick="callSubmit()">Log in</a>
				        <a href="javascript:;" class="btn" onclick="callClear()">Clear</a>
				    </section>
			    </fieldset>
			    <center>
			    	<a href="javascript:;" class="btn" outline onclick="callCreateNewAccount()">Create New Account</a>
			    </center>
			</div>
			<div class="message message-error" id="loginMessage" style="dispaly:none;">
				Invalid User Name and Password
				<footer>
					<a href="javascript:;" class="btn" type="white" small="" outline="" onclick="callClear()">Got it</a>
				</footer>
			</div>
		</form>
	</body>
</html>
<script>
	if (document.forms[0].loginFlag.value == 'N') {
		document.getElementById('loginMessage').style.display = 'block';
	}
</script>