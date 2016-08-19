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
						<th><nested:write property="columnName"/></th>               				
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
        	<nested:present property="queryDefinitionLineVOList">
            	<nested:iterate property="queryDefinitionLineVOList" id="queryDefinitionLineVOList" 
               		type="com.dataprocess.bods.vo.QueryDefinitionLineVO" indexId="count">
					<tr>
						<nested:iterate property="queryDefinitionDataVOList" id="queryDefinitionDataVOList" 
               				type="com.dataprocess.bods.vo.QueryDefinitionDataVO" indexId="count1">
               					<td>
               						<nested:write property="dataValue"/>
               					</td>
               			</nested:iterate>		
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
    $("tbody").each(function() {
    	var $this = $(this);
    	var newrows = [];
    	$this.find("tr").each(function(){
    		var i = 0;
    		$(this).find("td").each(function(){
    			i++;
    			if(newrows[i] == undefined) {
    				newrows[i] = $("<tr></tr>");
    			}
    			newrows[i].append($(this));
    		});
    	});
    	$this.find("tr").remove();
    	$.each(newrows, function(){
    		$this.append(this);
    	});
    });
} );
</script>