<!DOCTYPE HTML>
<%@ taglib uri="tiles" prefix="tiles" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page pageEncoding="UTF-8"%>
<head>
<META NAME="Description" CONTENT="BODS">
<link rel="shortcut icon" href="/images/etl_favicon.png" />
</head>
<link href="css/materialize.css" rel='stylesheet' type='text/css' />
<style>
	#loading {
    	background: linear-gradient(rgba(0,0,0,.5), rgba(0,0,0,.5));/* Standard syntax */
    	background: -moz-linear-gradient(rgba(0,0,0,.5), rgba(0,0,0,.5));/* For Firefox 3.6 to 15 */
    	background: -webkit-linear-gradient(rgba(0,0,0,.5), rgba(0,0,0,.5)); /* For Safari 5.1 to 6.0 */
    	background: -o-linear-gradient(rgba(0,0,0,.5), rgba(0,0,0,.5));/* For Opera 11.1 to 12.0 */
    	position: absolute;
    	top: 0px;
    	left: 0px;
    	width: 100%;
    	height: 100%;
	}
</style>
<script src="js/materialize.js"></script>
<body marginwidth="0" marginheight="0">
<tiles:insert attribute="page.head" ignore="true"/>
<tiles:insert attribute="page.menu" ignore="true"/>
<tiles:insert attribute="page.body" ignore="true"/>
<tiles:insert attribute="page.footer" ignore="true"/>
<div id='loading' style='z-index:1000;display: none;'>
	<p style="position:absolute;left:50%; top: 50% ">
		<img src="../images/progess_tick.gif" title="Loading" />please wait..
	</p>
</div>
</body>