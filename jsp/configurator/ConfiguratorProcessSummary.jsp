<%@ page language="java"%><%@ taglib uri="struts-html" prefix="html"%><%@ taglib uri="struts-bean" prefix="bean"%><%@ taglib uri="struts-nested" prefix="nested"%>

<html:html>
	<head>
		<link href="css/basepagemaker.css" rel='stylesheet' type='text/css'/>
		<link rel="stylesheet" href="css/processsummary.css">
	</head>
	<script>
	var isTimerStarted = false;
	var myInterval = 0;
	
	var callIntervalExist = false;
	function callValidationProcess() {
		document.configuratorForm.action='/bods/PrevalidationProcessLaunchMapping.etl';
		document.configuratorForm.submit();
	}
	
	
	function startTimer() {
		if (!isTimerStarted) {
			myInterval = setInterval("checkRunningStatus()",5000);
			isTimerStarted = true;
		}
	}
	
	function stopTimer() {
		if (isTimerStarted) {
			clearInterval(myInterval);
			isTimerStarted = false;
		}
		myInterval = 0;
	}
	
	function checkRunningStatus() {
		$.ajax({
			 async : false,
			 type: "POST",
		     dataType: "text",
		     url: "/bods/CheckingConfiguratorRunningStautsMapping.etl?configuratorExecId="+ $('#loaderExecutionId').val(),
		     success: function(data) {
		    	changingStatus(data);
		     }, error: function(xhr, status, error) {
		            console.log(xhr);
		            console.log(status);
		            console.log(error);
		       }
		})
	}
	
	function completedRecordDisplay() {
		$.ajax({
			 async : false,
			 type: "POST",
		     dataType: "text",
		     url: "/bods/CompletedRecordDisplayMapping.etl",
		     success: function(data) {
		    	 displayCount(data);
		     }, error: function(xhr, status, error) {
		            console.log(xhr);
		            console.log(status);
		            console.log(error);
		       }
		})
	}
	
	function changingStatus(executionStatus) {
		if(executionStatus == 'EXTRACT_START') {
			$('#statusFlow ul li:nth-child(1)').addClass("active");
		} else if(executionStatus == 'EXTRACT_END') {
			$('#statusFlow ul li:nth-child(1)').addClass("previous visited");
		} else if(executionStatus == 'STAGING_START') {
			$('#statusFlow ul li:nth-child(1)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(2)').addClass("active");
		} else if(executionStatus == 'STAGING_END') {
			$('#statusFlow ul li:nth-child(1)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(2)').addClass("previous visited");
		} else if(executionStatus == 'PREVALIDATION_START') {
			$('#statusFlow ul li:nth-child(1)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(2)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(3)').addClass("active");
		} else if(executionStatus == 'PREVALIDATION_END') {
			$('#statusFlow ul li:nth-child(1)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(2)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(3)').addClass("active");
			stopTimer();
		} else if(executionStatus == 'LOADING_START') {
			startTimer();
			$('#statusFlow ul li:nth-child(1)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(2)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(3)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(4)').addClass("active");
		} else if(executionStatus == 'LOADING_END') {
			$('#statusFlow ul li:nth-child(1)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(2)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(3)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(4)').addClass("previous visited");
		} else if(executionStatus == 'COMPLETED') {
			$('#statusFlow ul li:nth-child(1)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(2)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(3)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(4)').addClass("previous visited");
			$('#statusFlow ul li:nth-child(5)').addClass("active");
			$('#afterExecutionDisplayId').show();
			callIntervalExist = true;
			stopTimer();
			completedRecordDisplay();
		}
	}
	
	function displayCount(data) {
		var records = data.split('##');
		var totalRecord = records[0];
		var successRecord = records[1];
		var errorRecord = records[2];
		$('#displayRecordDiv td:nth-child(1)').text(totalRecord);
		$('#displayRecordDiv td:nth-child(2)').text(totalRecord);
		$('#displayRecordDiv td:nth-child(3)').text(errorRecord);
		$('#displayRecordDiv td:nth-child(4)').text(errorRecord);
		$('#displayRecordDiv td:nth-child(5)').text(successRecord);
	}
	
	function openFile(statusMsg) {
		document.configuratorForm.action='/bods/DisplayRecordFileMapping.etl?type='+statusMsg;
		document.configuratorForm.submit();
	}
</script>
<nested:root name="configuratorForm">
	<html:form styleId="ldrForm" action="PrevalidationProcessLaunchMapping">
		<nested:nest property="configuratorVO">
			<nested:hidden property="loaderExecutionId" styleId="loaderExecutionId"/>
			<nested:hidden property="configuratorName" styleId="configuratorName"/>
		<div id="pageContentDivId">
			<div class="div-table">
				<div class="checkout-wrap" id="statusFlow">
	  				<ul class="checkout-bar">
						<li><a href="#">Extraction</a></li>
						<li>Staging</li>
						<li onclick="callValidationProcess()">Validation & Translation</li>
						<li>Loading</li>
						<li>Complete</li>
	  				</ul>
	  			</div>
  			</div>
  			<div style="margin-top: 200px;height: 100px;display: none;" id="afterExecutionDisplayId">
  				<table cellpadding="0" cellspacing="0" border="0" width="" style="border:1px solid black;width: 90%;margin-left: 57px;" class="cs-sap-table">
					<thead>
				    	<tr>
			      			<th style="padding-left: 30px;">Total Record</th>
			               	<th style="padding-left: 20px;">Processed Record</th>
			                <th style="padding-left: 45px;">UnProcessed Record</th>
			                <th style="padding-left: 45px;">Error Record</th>
			                <th style="padding-left: 70px;">Success Record</th>
			                <th style="padding-left: 70px;">Output File</th>
						</tr>
			        </thead>
			            <tr id="displayRecordDiv">
							<td style="padding-left: 30px;"></td>
							<td style="padding-left: 20px;"></td>
							<td style="padding-left: 45px;"></td>
							<td style="padding-left: 70px;"></td>
							<td style="padding-left: 85px;"></td>
							<td style="padding-left: 85px;">
								<img src="images/excel_generate_green.gif" onclick="openFile('Success')"/>
								<img src="images/excel_generate_red.gif" onclick="openFile('Error')"/>
							</td>
						</tr>
			      </table>
  			</div>
		</div>
		</nested:nest>
	</html:form>
</nested:root>
<script>
	if(!callIntervalExist) {
		startTimer();
	}
</script>
</html:html>



    


 