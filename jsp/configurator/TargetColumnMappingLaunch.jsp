<%@ page language="java"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<html:html>
	<head>
		<link href="css/basepagemaker.css" rel='stylesheet' type='text/css' />
	</head>
	<script>
	
	var columnNameArr;
	var attributeNameArr;
	var editColumnIndex = 0;
	var isEditRow = false;
	
	function callExecute() {
		document.configuratorForm.action='/bods/ConfiguratorSaveAction.etl';
		document.configuratorForm.submit();
	}
	
	function addColumnToTarget() {
		var tbBodyChildrenlength = $('#tableBodyId tr').length;
		if(!isEditRow) {
			if(tbBodyChildrenlength > 1) {
				tbBodyChildrenlength = tbBodyChildrenlength + 1;
			}
		} else {
			tbBodyChildrenlength = editColumnIndex;
		}
		newRowScript(tbBodyChildrenlength);
	}
	
	function editColumn(tbBodyChildrenIndex) {
		var editColumnName = $('#columnNameTdId'+tbBodyChildrenIndex).val();
		var editAttributeName = $('#attributeNameTdId'+tbBodyChildrenIndex).val();
		
		$('#columnName').val(editColumnName);
		$('#attributeName').val(editAttributeName);
		editColumnIndex = tbBodyChildrenIndex;
		isEditRow = true;
	}
	
	function deleteColumn(tbBodyChildrenIndex) {
		$('#rowCount'+tbBodyChildrenIndex).remove();
		var row = '';
		row+='	<input type="hidden" name="configuratorVO.configuratorInterfaceColumnVOListList['+tbBodyChildrenIndex+'].mappedColumnFlag" value="N">';
	}
	
	function newRowScript(tbBodyChildrenlength) {
		var row = '';
		if(isEditRow) {
			$('#rowCount'+tbBodyChildrenlength).remove();
			isEditRow = false;
		}
		row+='<tr id="rowCount'+tbBodyChildrenlength+'">';
		row+='	<td style="padding-left: 30px;">' + $('#tableNameId').val() + '</td>';
		row+='	<td style="padding-left: 20px;">' + $('#columnName').val() + '</td>';
		row+='	<td style="padding-left: 45px;">' + $('#attributeName').val() + '</td>';
		row+='	<td style="padding-left: 172px;" id="custom-edit">';
		row+='		<i class="fa fa-pencil-square-o fa-lg" style="color:#2699D0;cursor: pointer;" onclick="editColumn('+tbBodyChildrenlength+')"></i>';
		row+='	</td>';
		row+='	<td style="padding-left: 85px;" id="custom-delete">';
		row+='		<i class="fa fa-trash-o fa-lg" style="color:red;cursor: pointer;" onclick="deleteColumn('+tbBodyChildrenlength+')"></i>';
		row+='	</td>';
		row+=' 	<input type="hidden" id="tableNameTdId'+tbBodyChildrenlength+'" value="'+$('#tableNameId').val()+'"/>';
		row+=' 	<input type="hidden" id="columnNameTdId'+tbBodyChildrenlength+'" value="'+$('#columnName').val()+'"/>';
		row+=' 	<input type="hidden" id="attributeNameTdId'+tbBodyChildrenlength+'" value="'+$('#attributeName').val()+'"/>';
		row+=' 	<input type="hidden" name="currentIndexId" value="currentIndexId'+tbBodyChildrenlength+'"/>';
		row+='	<input type="hidden" name="configuratorVO.configuratorInterfaceColumnVOList['+tbBodyChildrenlength+'].tableName" value="'+$('#tableNameId').val()+'">';
		row+='	<input type="hidden" name="configuratorVO.configuratorInterfaceColumnVOList['+tbBodyChildrenlength+'].columnName" value="'+$('#columnName').val()+'">';
		row+='	<input type="hidden" name="configuratorVO.configuratorInterfaceColumnVOList['+tbBodyChildrenlength+'].attributeName" value="'+$('#attributeName').val()+'">';
		row+='	<input type="hidden" name="configuratorVO.configuratorInterfaceColumnVOList['+tbBodyChildrenlength+'].mappedColumnFlag" value="Y">';
		row+='</tr>';
		$('#tableBodyId').append(row);
	}
	
	$(document).ready(function() {
		$('#tableNameId').change(function() {
			getTableColumnName();
		})
	});
	
	function getTableColumnName() {
		$.ajax({
			 async : false,
			 type: "POST",
		     dataType: "text",
		     url: "/bods/GetTargetTableColumnMapping.etl?tableName="+ $('#tableNameId').val(),
		     success: function(data) {
		    	 var jsonparse = $.parseJSON(data);
		    	 columnNameArr = jsonparse.configuratorTableColVOList;
		    	 attributeNameArr = jsonparse.configuratorAttributeColVOList;
		    	 dynamicColumnNameFormation();
		    	 dynamicAttributeNameFormation();
		     }, error: function(xhr, status, error) {
		            console.log(xhr);
		            console.log(status);
		            console.log(error);
		       }
		})
	}
	
	function dynamicColumnNameFormation() {
		var currentDivObject = $('#dynamicColumnNameId');
		$(currentDivObject).empty();
		var leftExpressionObject = '<select id=\"columnName\" name=\"columnName\" style=\"width: 150px;margin-left: -104px;\">';
		var opts = [];
		if(columnNameArr != '') {
			leftExpressionObject += '<option value="">--select--</option>';
			for(var i=0; i < columnNameArr.length;i++) {
				leftExpressionObject += '<option value="' + columnNameArr[i].columnName + ' ">' + columnNameArr[i].columnName + '</option>';
			}
			leftExpressionObject += '</select>'
			currentDivObject.append(leftExpressionObject);
		} 
	} 

	function dynamicAttributeNameFormation() {
		var currentDivObject = $('#dynamicAttributeNameId');
		$(currentDivObject).empty();
		var rightExpressionObject = '<select id=\"attributeName\" name=\"attributeName\" style=\"width: 150px;margin-left: -78px;\">';
		var opts = [];
		if(attributeNameArr != '') {
			rightExpressionObject += '<option value="">--select--</option>';
			for(var i=0; i < attributeNameArr.length;i++) {
				rightExpressionObject += '<option value="' + attributeNameArr[i].columnName + ' ">' + attributeNameArr[i].columnName + '</option>';
			}
			rightExpressionObject += '</select>'
			currentDivObject.append(rightExpressionObject);
		} 
	}
	</script>
	<style>
		#custom-edit, custom-delete {
			display: table-cell;
			font: normal normal normal 14px/1 FontAwesome;
    		font-size: inherit;
    		text-rendering: auto;
    		-webkit-font-smoothing: antialiased;
		}
		.fa-pencil-square-o:hover::before {
  			color: #FFA200;
		}
		.fa-trash-o:hover::before {
  			color: #FFA200;
		}
		.fa-plus-circle:hover::before {
			color: #FFA200;
		}
	</style>
	<nested:root name="configuratorForm">
		<html:form styleId="ldrForm" action="ConfiguratorColumnDefinition">
			<nested:nest property="configuratorVO">
				<nested:nest property="configuratorInterfaceColumnVO">
					<div id="pageContentDivId">
						<div class="div-table">
							<div class="div-table-row">
								<div class="div-table-col" style="width: 200px;">
									<span style="padding-left: 1px;"> Table Name </span>
								</div>
								<div class="div-table-col">
									<nested:text property="tableName" style="width: 150px;margin-left: -112px;" styleId="tableNameId"/>
								</div>
								<div class="div-table-col">
									<span style="padding-left: 5px;"> Column Name</span>
								</div>
								<div class="div-table-col" id="dynamicColumnNameId">
									<%-- <nested:text property="columnName" style="width: 150px;margin-left: -104px;"/> --%>
								</div>
								<div class="div-table-col">
									<span style="padding-left: 12px;"> Attribute Name </span>
								</div>
								<div class="div-table-col" id="dynamicAttributeNameId">
									<%-- <nested:text property="attributeName" style="width: 150px;margin-left: -78px;"/>	 --%>
								</div>
								<div class="div-table-col" style="cursor: pointer;" >
									<i class="fa fa-plus-circle fa-lg" style="color:#2699D0;" onclick="addColumnToTarget()"></i>
								</div>
							</div>
						</div>
						<br/>
						<table cellpadding="0" cellspacing="0" border="0" width="" style="border:1px solid black;width: 80%;margin-left: 120px;" class="cs-sap-table">
			        		 <thead>
			            		<tr>
					      			<th style="padding-left: 30px;">Table Name</th>
					               	<th style="padding-left: 20px;">Column Name</th>
					                <th style="padding-left: 45px;">Attribute Name</th>
					                <th style="padding-left: 165px;">Edit</th>
					                <th style="padding-left: 70px;">Delete</th>
					      		</tr>
			            	 </thead>
			            	 <tbody id="tableBodyId">
			            	 	<nested:present property="configuratorInterfaceColumnVOList">
			               	 		<nested:iterate property="configuratorInterfaceColumnVOList" id="configuratorInterfaceColVOId" 
			               				type="com.dataprocess.bods.vo.ConfiguratorInterfaceColumnVO" indexId="count">
					               			<nested:equal property="mappedColumnFlag" value="Y">
					               				<tr id="rowCount"+<%=count%>>
								               		<td style="padding-left: 30px;"><nested:write property="tableName"/></td>
									               	<td style="padding-left: 20px;"><nested:write property="columnName"/></td>
									                <td style="padding-left: 45px;"><nested:write property="attributeName"/></td>
									                <nested:hidden property="tableName" styleId="tableNameTdId<%=count%>"/>
									                <nested:hidden property="columnName" styleId="columnNameTdId<%=count%>"/>
									                <nested:hidden property="attributeName" styleId="attributeNameTdId<%=count%>"/>
									                <td style="padding-left: 172px;" id="custom-edit">
									                	<i class="fa fa-pencil-square-o fa-lg" style="color:#2699D0;cursor: pointer;" onclick="editColumn(<%=count%>)"></i>
									                </td>
									                <td style="padding-left: 85px;" id="custom-delete">
									                	<i class="fa fa-trash-o fa-lg" style="color:red;cursor: pointer;" onclick="deleteColumn(<%=count%>)"></i>
									                </td>
								                </tr>
							                </nested:equal>
			               			</nested:iterate>
			               		</nested:present>
			            	 </tbody>
            			</table>
					</div>
					<center>
						<button class="btn waves-effect waves-light" type="button" onclick="callExecute()">Save</button>
					</center>
				</nested:nest>
			</nested:nest>
		</html:form>
	</nested:root>
</html:html>