<%@ taglib uri="tiles" prefix="tiles"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
	<div id="page-wrapper" class="gray-bg dashbard-1">
		<div class="content-main">

			<!--banner-->
			<div class="banner">
				<h2>
					<a href="/bods/home.etl">Home</a> <i class="fa fa-angle-right"></i> <span id="pagination"></span>
				</h2>
			</div>
			<div class="blank" style="min-height: 450px !important;">
				<div class="blank-page">
					<logic:present name="transactionSuccessMessage">
						<%-- <div id="TrnMessage" class="" align='center'>
							<bean:define id="message" name="transactionMessage" type="com.echain.util.TransactionMessage" />
						</div>
						*<![CDATA[*/document.getElementById("TrnMessage").className="<%=message.getCssClass()%>";window.status=document.getElementById("TrnMessage").innerHTML;/*]]>*/</script> --%>
						<div class="alert alert-success" role="alert">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 						<%-- <bean:define id="transactionMessage" name="transactionMessage" type="com.dataprocess.bods.util.TransactionMessage" /> --%>
	 						<bean:write name="transactionSuccessMessage" property="transactionMessage" />
	  					</div>
					</logic:present>
					<logic:present name="transactionFailureMessage">
						<%-- <div id="TrnMessage" class="" align='center'>
							<bean:define id="message" name="transactionFailureMessage" type="com.echain.util.TransactionMessage" />
						</div>
						<script>/*<![CDATA[*/document.getElementById("TrnMessage").className="<%=message.getCssClass()%>";window.status=document.getElementById("TrnMessage").innerHTML;/*]]>*/</script> --%>
					
						<div class="alert alert-danger" role="alert">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 						<bean:define id="transactionMessage" name="transactionMessage" type="com.dataprocess.bods.util.TransactionMessage" />
	 						<!-- <div class="" role="alert"><strong>Transaction Failure!</strong> Better check yourself, you're not looking too good.</div> -->
	  					</div>
					</logic:present>
				<tiles:insert attribute="content" />
				</div>
			</div>
			<div class="copy">
				<p>
					&copy; 2016 BODS. All Rights Reserved
				</p>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>