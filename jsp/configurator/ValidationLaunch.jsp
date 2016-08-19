<%@ page language="java" %><%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>

<script>
	var queryColumnNameArr;
	var configuratorColumnVOArr;
	var conditionArr = '';
	
	function callSubmit() {
		document.configuratorForm.action='/bods/ValidationSaveMapping.etl';
		document.configuratorForm.submit();
	}
	
	function doNextProcess() {
		document.configuratorForm.action='/bods/TargetColumnMappingLaunch.etl';
		document.configuratorForm.submit();
	}
	
	$(document).ready(function() {
		$('#queryParserId').click(function() {
			invokeQueryParse();
		})
		
		$('#successId').change(function() {
			if(this.checked) {
				$('#conversionBlock').show();
			}
		})
	
		$('#errorId').change(function() {
			if(this.checked) {
				$('#conversionBlock').hide();
			}
		})
	});
	
	function getRightExprValue() {
		var selectVal = $('#rightExpressionType').val();
		if(selectVal=='Constant') {
			var currentDivObject = $('#dynamicRightExpressionValueId');
			$(currentDivObject).empty();
			var rightExpressionObject = '<input type=\"text\" id=\"rightExpressionValue\" name=\"rightExpressionValue\" style=\"width: 150px;margin-left: -71px;\"/>';
			currentDivObject.append(rightExpressionObject);
		} else {
			dynamicConfiguratorColumnFormation();
		}
	}
	
	function invokeQueryParse() {
		var queryValue = $('#validateQry').val();
		$.ajax({
			 async : false,
			 type: "POST",
		     dataType: "text",
		     url: "/bods/QueryValidationMapping.etl?query="+encodeURIComponent(queryValue),
		     success: function(data) {
		    	 if(data != null && data != '') {
		    		 var conditionVO = $.parseJSON(data);
			    	 queryColumnNameArr = conditionVO.configuratorVO.queryColumnNameList;
			    	 configuratorColumnVOArr = conditionVO.configuratorVO.configuratorColumnVOList;
			    	 dynamicQueryColumnFormation();
			    	 dynamicConfiguratorColumnFormation();
		    	 }
		     }, error: function(xhr, status, error) {
		            console.log(xhr);
		            console.log(status);
		            console.log(error);
		       }
		})
	}
	
	function dynamicQueryColumnFormation() {
		var currentDivObject = $('#dynamicLeftExpressionValueId');
		$(currentDivObject).empty();
		var leftExpressionObject = '<select id=\"leftExpressionValue\" name=\"leftExpressionValue\" style=\"width: 150px;margin-left: -91px;\">';
		var opts = [];
		if(queryColumnNameArr != '') {
			for(var columnName in queryColumnNameArr) {
				leftExpressionObject += '<option value="' + queryColumnNameArr[columnName] + ' ">' + queryColumnNameArr[columnName] + '</option>';
			}
			leftExpressionObject += '</select>'
			currentDivObject.append(leftExpressionObject);
		} 
	} 

	function dynamicConfiguratorColumnFormation() {
		var currentDivObject = $('#dynamicRightExpressionValueId');
		$(currentDivObject).empty();
		var rightExpressionObject = '<select id=\"rightExpressionValue\" name=\"rightExpressionValue\" style=\"width: 150px;margin-left: -71px;\">';
		var opts = [];
		if(configuratorColumnVOArr != '') {
			for(var i=0; i < configuratorColumnVOArr[0].length;i++) {
				rightExpressionObject += '<option value="' + configuratorColumnVOArr[0][i].columnName + ' ">' + configuratorColumnVOArr[0][i].columnName + '</option>';
			}
			rightExpressionObject += '</select>'
			currentDivObject.append(rightExpressionObject);
		} 
	}
	
	function dynamicConversionLeftColumn() {
		var currentDivObject = $('#dynamicConversionLeftColId');
		$(currentDivObject).empty();
	}
	
	function addCondition() {
		if(conditionArr == '') {
			conditionArr = conditionStr();
		} else {
			conditionArr += conditionStr();
		}
		$('#condtStrId').val(conditionArr);
	}
	
	function conversionCondition() {
		
	}
	
	function conditionStr() {
		var cont = '';
		if($('#leftExpressionType').val() == 'Query' && $('#rightExpressionValue').val() != '' ) {
			cont =  "STGTBL." + $('#leftExpressionValue').val() +   $('#operator option:selected').val()  +  "PVQUERY." + $('#rightExpressionValue').val();
		} else {
			cont =  "STGTBL." + $('#leftExpressionValue').val() +  $('#operator option:selected').val();
		}
		return cont;
	}
</script>
<html:html>
<head>
	<link href="css/basepagemaker.css" rel='stylesheet' type='text/css' />
</head>
	<nested:root name="configuratorForm">
		<html:form styleId="configuratorForm1">
			<nested:nest property="configuratorVO">
				<nested:nest property="configuratorValidationVO">
					<div id="pageContentDivId" style="visibility:visible;height:400px;width:100%;overflow-y: scroll;position:relative">
						<div class="div-table">
							<div class="div-table-row">
								<div class="div-table-col">
									<span> Validation Name </span>
								</div>
								<div class="div-table-col">
									<nested:text property="name"/>
								</div>
								<div class="div-table-col">
									<span> Display Name </span>
								</div>
								<div class="div-table-col">
									<nested:text property="displayName"/>	
								</div>
							</div>
							
							<div class="div-table-row">
								<div class="div-table-col">
									<span> Validation Inference </span>
								</div>
								<div class="div-table-col">
									<nested:radio property="validationInference" value="Success" styleId="successId"> &nbsp; Success </nested:radio> &nbsp;
									<nested:radio property="validationInference" value="Error" styleId="errorId"> &nbsp; Error </nested:radio>
								</div>
								<div class="div-table-col" style="margin-left: 108px;">
										<span> Active </span>
									</div>
								<div class="div-table-col" style="margin-left: -52px;">
									<nested:radio property="active" value="Y"> &nbsp; Yes </nested:radio> &nbsp;
									<nested:radio property="active" value="N"> &nbsp; No </nested:radio>
								</div>
							</div>
							
							<div class="div-table-row">
								<div class="div-table-col">
									<span> Query </span>
								</div>
								<div class="div-table-col">
									<nested:textarea property="validationQuery" rows="4" cols="50" style="resize:none;" styleId="validateQry"/>
								</div>
								<div class="div-table-col" style="margin-left: 55px;margin-top: 40px;">
									<button class="btn waves-effect waves-light" name="queryParse" id="queryParserId" type="button">Query Parse</button>
								</div>	
							</div>
							
							<div>
								<h4><b><u> Condition </u></b></h4>
								<div class="div-table-row">
									<div class="div-table-col">
										<span style="padding-left: 12px;"> Left Expression </span>
									</div>
									<div class="div-table-col">
										<nested:select styleId="leftExpressionType" property="leftExpressionType" style="width: 150px;margin-left: -91px;">
											<html:option value="">--Select--</html:option>
											<html:option value="Query">Query Column</html:option>
										</nested:select>
									</div>
									<div class="div-table-col">
										<span style="padding-left: 12px;">Operator</span>
									</div>
									<div class="div-table-col">
											<nested:select styleId="operator" property="operator" style="width: 150px;margin-left: -143px;">
											<html:option value="">--Select--</html:option>
											<html:option value=" IS NULL ">IS NULL</html:option>
											<html:option value=" IS NOT NULL ">IS NOT NULL</html:option>
											<html:option value=" IN ">IN</html:option>
											<html:option value=" != ">!=</html:option>
											<html:option value=" = ">=</html:option>
											<%-- <html:option value=" '' "> < </html:option> --%>
											<html:option value=" <= "> <= </html:option>
											<html:option value=" > "> > </html:option>
											<html:option value=" >= "> >= </html:option>
										</nested:select>
									</div>
									<div class="div-table-col">
										<span style="padding-left: 12px;"> Right Expression </span>
									</div>
									<div class="div-table-col">
										<nested:select styleId="rightExpressionType" property="rightExpressionType" style="width: 150px;margin-left: -78px;" onchange="getRightExprValue()">
											<html:option value="">--Select--</html:option>
											<html:option value="Constant">Constant</html:option>
											<html:option value="Configurator">Configurator Column</html:option>
										</nested:select>
									</div>
								</div>
								
								<div class="div-table-row">
									<div class="div-table-col">
										<span style="padding-left: 12px;"> Left Column </span>
									</div>
									<div class="div-table-col" id="dynamicLeftExpressionValueId">
										<%-- <nested:text property="leftExpressionValue" style="width: 150px;margin-left: -91px;"/> --%>
									</div>
									<div class="div-table-col">
										<span style="padding-left: 12px;"></span>
									</div>
									<div class="div-table-col">
										<nested:hidden property="operator" styleClass="width: 150px;margin-left: -143px;"/>	
									</div>
									<div class="div-table-col">
										<span style="padding-left: 20px;"> Right Column </span>
									</div>
									<div class="div-table-col" id="dynamicRightExpressionValueId">
										<%-- <nested:text property="rightExpressionValue" style="width: 150px;margin-left: -71px;"/>	 --%>
									</div>
								</div>
								<div class="div-table-row">
									<div class="div-table-col">
										<span></span>
									</div>
									<div class="div-table-col">
										<nested:textarea property="validationConditionString" rows="4" cols="50" style="resize:none;" styleId="condtStrId"/>
									</div>
									<div class="div-table-col" style="margin-left: 38px;margin-top: 40px;">
										<button class="btn waves-effect waves-light" type="button" onclick="addCondition()">Add Condition</button>
									</div>
								</div>
								
								<div class="div-table-row" id="dynamicConversionLeftId">
									<h4><b><u> Conversion </u></b></h4>
									<div class="div-table-col">
										<span style="padding-left: 12px;"> Left Column </span>
									</div>
									<div class="div-table-col" id="dynamicConversionLeftColId">
										<%-- <nested:text property="leftExpressionValue" style="width: 150px;margin-left: -91px;"/> --%>
									</div>
									<div class="div-table-col">
										<span style="padding-left: 12px;"></span>
									</div>
									<div class="div-table-col">
										<nested:hidden property="operator" styleClass="width: 150px;margin-left: -143px;"/>	
									</div>
									<div class="div-table-col">
										<span style="padding-left: 20px;"> Right Column </span>
									</div>
									<div class="div-table-col" id="dynamicConversionRightColId">
										<%-- <nested:text property="rightExpressionValue" style="width: 150px;margin-left: -71px;"/>	 --%>
									</div>
								</div>
								<div class="div-table-row" id="dynamicConversionCondtId">
									<div class="div-table-col">
										<span></span>
									</div>
									<div class="div-table-col">
										<nested:textarea property="validationConversion" rows="4" cols="50" style="resize:none;" styleId="condtStrId"/>
									</div>
									<div class="div-table-col" style="margin-left: 38px;margin-top: 40px;">
										<button class="btn waves-effect waves-light" type="button" onclick="conversionCondition()">Conversion Condition</button>
									</div>
								</div>
								
								<h4><b><u> Error Message </u></b></h4>
								<div class="div-table-row">
									<div class="div-table-col" style="margin-left: 250px;">
										<nested:textarea property="validationErrorMessage" rows="2" cols="50" style="resize:none;" />
									</div>
								</div>		
							</div>
						</div>
						<center>
							<button class="btn waves-effect waves-light" type="submit" onclick="callSubmit()">Save</button> &nbsp; &nbsp;
							<button class="btn waves-effect waves-light" type="button" onclick="doNextProcess()">Next</button>
						</center> &nbsp;
					</div>
				</nested:nest>
			</nested:nest>
		</html:form>
	</nested:root>
</html:html>