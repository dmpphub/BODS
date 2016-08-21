<%@ taglib uri="struts-html" prefix="html" %><%@ taglib uri="struts-bean" prefix="bean" %><%@ taglib uri="struts-nested" prefix="nested" %>
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="css/jquery.dataTables.min.css">
<script>
	function fetchRecord(count) {
		location.href = '/bods/ValidationLaunch.etl?name='+document.getElementById("name"+count).value;
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
<html:form action="/ValidationLaunch.etl">
	<nested:root name="configuratorForm">
		<nested:nest property="configuratorVO">
				<table id="example" class="cell-border hover" cellpadding="5px" cellspacing="5px" border="0" width="60%">
        		 <thead>
            		<tr>
		      			<th>Validation Name</th>
		               	<th>Display Name</th>
		      		</tr>
            	 </thead>
            	 <tbody>
               		<nested:present property="configuratorValidationVOList">
               			<nested:iterate property="configuratorValidationVOList" id="configuratorValidationVOList" 
               				type="com.dataprocess.bods.vo.ConfiguratorValidationVO" indexId="count">
                			<tr onclick="fetchRecord('<%= count %>')" style="cursor: pointer;">
                				<td>
                					<nested:write property="name"/>
                					<nested:hidden property="name" styleId="<%= \"name\" + count %>"/>
                				</td>
                				<td>
                					<nested:write property="displayName"/>
                				</td>
                			</tr>
               			</nested:iterate>
               		</nested:present>
               	</tbody>	
	</table>
		</nested:nest>
	</nested:root>
</html:form>
</html:html>
<script type="text/javascript">
$(document).ready(function() {
    $('#example').DataTable( {
    	columnDefs: [
	{
		targets: [ 0, 1, 2 ],
		className: 'mdl-data-table__cell--non-numeric'
	}
    	         	]
    } );
} );
</script>