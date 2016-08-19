<%@ taglib uri="tiles" prefix="tiles"%>
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