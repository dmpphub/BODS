<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>

<style>
	.div-table{
	  display:table;         
	  width:100%;      
	  border-spacing:5px;
	}
	.div-table-row{
	  display:table-row;
	  width:auto;
	  clear:both;
	}
	.div-table-col{
		float: left;
		display: table-column;
	    width: auto;
	    line-height: 25px;
	    align: middle;
	    margin: 10px;
	}
	.div-table-col span {
		float:left;
		width: 220px;
		padding-left: 34px;
	}
	input[type="text"], input[type="Password"], select{
		width:200px;
	    -ms-box-sizing:border-box;
	    -moz-box-sizing:border-box;
	    box-sizing:border-box;
	    -webkit-box-sizing:border-box;
	    height:25px; 
	}
	#pageContentDivId {
		width:100%;
		height: 410px;
		background-color:#eee;
	}
</style>

<script>
	function navigate() {
		document.forms[0].action = '/bods/InstanceDetailsAction.etl';
		document.forms[0].submit();
	}
</script>

<html:html>
	<html:form action="/InstanceDetailsAction.etl">
		<nested:root name="instanceDetailsForm">
			<nested:nest property="instanceDetailsVO">
				<div id="pageContentDivId">
					<div class="div-table">
						<div class="div-table-row">
							<div class="div-table-col">
								<span>
									Connection Name
								</span>
							</div>
			                <div class="div-table-col">
			                	<nested:text property="instanceName"/>
								<nested:hidden property="instanceId"/>
			                </div>
			                <div class="div-table-col">
			                	<span>
		                			Display Name
		                		</span>
			                </div>
			                <div class="div-table-col">
			                	<nested:text property="displayName"/>
			                </div>
						</div>
		            
						<div class="div-table-row">
							<div class="div-table-col">
								<span>
									Description
								</span>
							</div>
							<div class="div-table-col">
								<nested:textarea property="description" rows="2" cols="40" style="resize:none;" />
							</div>
						</div>
						
						<div class="div-table-row">
							<div class="div-table-col">
								<span>
									DB User Name
								</span>
							</div>
							<div class="div-table-col">
								<nested:text property="dbUserName"/>
							</div>
							<div class="div-table-col">
								<span>
									DB Password
								</span>
							</div>
							<div class="div-table-col">
								<nested:text property="dbPassword"/>
							</div>
						</div>
						
						<div class="div-table-row">
							<div class="div-table-col">
								<span>
									Host Name
								</span>
							</div>
							<div class="div-table-col">
								<nested:text property="hostName"/>
							</div>
							<div class="div-table-col">
								<span>
									Port
								</span>
							</div>
							<div class="div-table-col">
								<nested:text property="port"/>
							</div>
						</div>
						
						<div class="div-table-row">
							<div class="div-table-col">
								<span>
									Service Name
								</span>
							</div>
							<div class="div-table-col">
								<nested:text property="serviceName"/>
							</div>
							<div class="div-table-col">
								<span>
									Instance Type
								</span>
							</div>
							<div class="div-table-col">
								<nested:checkbox property="instanceType" value="Source" > &nbsp; Source</nested:checkbox>  &nbsp;
								<nested:checkbox property="instanceType" value="destinatin" > &nbsp; Destination</nested:checkbox>
							</div>
						</div>
						<br/>
						<center>
							<button class="btn waves-effect waves-light" type="submit" name="action">Save</button>
						</center>
					</div>
				</div>
			</nested:nest>
		</nested:root>
	</html:form>
<script>
	$('#pagination').html("<a href='/bods/InstanceDetailsForm.etl'>Instance Details</a>");
</script>
</html:html>