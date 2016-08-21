<%@ taglib uri="struts-html" prefix="html" %><%@ taglib uri="struts-bean" prefix="bean" %><%@ taglib uri="struts-nested" prefix="nested" %>
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="css/jquery.dataTables.min.css">
<script>
	function doNextProcess() {
		location.href = "/bods/QueryDefinitionColumnList.etl";
	}
</script>
<style>
		
</style>
<html:html>
	<html:form action="/QueryDefinitionSaveAction">
		<nested:nest property="queryDefinitionVO">
		<table id="example" class="display" cellspacing="0" width="100%">
        <thead>
            <tr>
                <nested:present property="queryDefinitionLineVOList">
            		<nested:iterate property="queryDefinitionLineVOList" id="queryDefinitionLineVOList" 
               				type="com.dataprocess.bods.vo.QueryDefinitionLineVO" indexId="count">
               		<nested:notEqual property="conversionColumn" value="Y">
						<th><nested:write property="columnName"/></th>       
					</nested:notEqual>        				
               		</nested:iterate>		
            	</nested:present>
            </tr>
        </thead>
        <!-- <tfoot>
            <tr>
                <th>Name</th>
                <th>Position</th>
                <th>Office</th>
                <th>Age</th>
                <th>Start date</th>
                <th>Salary</th>
            </tr>
        </tfoot> -->
        <tbody>
        	<nested:present property="queryDefinitionDataVOList">
            	<nested:iterate property="queryDefinitionDataVOList" id="queryDefinitionLineVOList" 
               		type="com.dataprocess.bods.vo.QueryDefinitionDataVO" indexId="count">
					<tr>
						<nested:present property="queryDefinitionDataVOList">
							<nested:iterate property="queryDefinitionDataVOList" id="queryDefinitionDataVOList" 
	               				type="com.dataprocess.bods.vo.QueryDefinitionDataVO" indexId="count1">
	               				<nested:notEqual property="uniqueFlag" value="Y">
	               					<td>
	               						<nested:write property="dataValue"/>
	               					</td>
	               				</nested:notEqual>
	               			</nested:iterate>							
						</nested:present>
					</tr>               				
               	</nested:iterate>
            </nested:present>
        </tbody>
    </table>
</body>
	</nested:nest>
	</html:form>
</html:html>
<script type="text/javascript">
$(document).ready(function() {
    $('#example').DataTable( {
        "pagingType": "full_numbers"
    } );
} );
</script>