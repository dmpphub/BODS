<%@page import="com.dataprocess.bods.BODSConstants"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <meta name="keywords"
	content="Minimal Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript">
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>
<link href="css/bootstrap.min.css" rel='stylesheet' type='text/css' />
<!-- Custom Theme files -->
<link href="css/style.css" rel='stylesheet' type='text/css' />
<link href="css/font-awesome.css" rel="stylesheet">
<script src="js/jquery.min.js">
</script>
<script src="js/bootstrap.min.js">
</script>
<script src="js/jquery.metisMenu.js"></script>
<script src="js/jquery.slimscroll.min.js"></script>
<link href="css/custom.css" rel="stylesheet">
<script src="js/custom.js"></script>
<script src="js/screenfull.js"></script>
<script>
	$(function() {
		$('#supported').text('Supported/allowed: ' + !!screenfull.enabled);

		if (!screenfull.enabled) {
			return false;
		}

		$('#toggle').click(function() {
			screenfull.toggle($('#container')[0]);
		});

	});
</script>

</head>
<body>
	<div id="wrapper">
		<!----->
		<nav class="navbar-default navbar-static-top" role="navigation">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<h1>
					<a class="navbar-brand" href="/bods/home.etl">BODS</a>
				</h1>
			</div>
			<div class=" border-bottom">
				<div class="full-left">
					<section class="full-top">
						<button id="toggle">
							<i class="fa fa-arrows-alt"></i>
						</button>
					</section>
					<form class=" navbar-left-right">
						<input type="text" value="Search..." onfocus="this.value = '';"
							onblur="if (this.value == '') {this.value = 'Search...';}">
						<input type="submit" value="" class="fa fa-search">
					</form>
					<div class="clearfix"></div>
				</div>
				<div class="drop-men">
					<ul class=" nav_1">
						<!-- <li class="dropdown at-drop"><a href="#"
							class="dropdown-toggle dropdown-at " data-toggle="dropdown"><i
								class="fa fa-globe"></i> <span class="number">5</span></a>
							<ul class="dropdown-menu menu1 " role="menu">
								<li><a href="#">
										<div class="user-new">
											<p>Parthiban</p>
											<span>40 seconds ago</span>
										</div>
										<div class="user-new-left">

											<i class="fa fa-user-plus"></i>
										</div>
										<div class="clearfix"></div>
								</a></li>
								<li><a href="#">
										<div class="user-new">
											<p>Parthi</p>
											<span>3 minutes ago</span>
										</div>
										<div class="user-new-left">

											<i class="fa fa-heart"></i>
										</div>
										<div class="clearfix"></div>
								</a></li>
								<li><a href="#">
										<div class="user-new">
											<p>Partha</p>
											<span>4 hours ago</span>
										</div>
										<div class="user-new-left">

											<i class="fa fa-times"></i>
										</div>
										<div class="clearfix"></div>
								</a></li>
								<li><a href="#">
										<div class="user-new">
											<p>Machi</p>
											<span>yesterday at 08:30am</span>
										</div>
										<div class="user-new-left">

											<i class="fa fa-info"></i>
										</div>
										<div class="clearfix"></div>
								</a></li>
								<li><a href="#">
										<div class="user-new">
											<p>New comments waiting approval</p>
											<span>Last Week</span>
										</div>
										<div class="user-new-left">

											<i class="fa fa-rss"></i>
										</div>
										<div class="clearfix"></div>
								</a></li>
								<li><a href="#" class="view">View all messages</a></li>
							</ul></li> -->
						<li class="dropdown"><a href="#"
							class="dropdown-toggle dropdown-at" data-toggle="dropdown"><span
								class=" name-caret"><%=session.getAttribute(BODSConstants.LOGGED_USER_NAME) %><i class="caret"></i></span><img
								src="images/manoj.jpg" height="60" width="60"></a>
							<ul class="dropdown-menu " role="menu">
								<li><a href="/bods/Logout.etl"><i class="fa fa-user"></i>Logout</a></li>
								<!-- <li><a href="inbox.html"><i class="fa fa-envelope"></i>Inbox</a></li>
								<li><a href="calendar.html"><i class="fa fa-calendar"></i>Calender</a></li>
								<li><a href="inbox.html"><i class="fa fa-clipboard"></i>Tasks</a></li> -->
							</ul></li>

					</ul>
				</div>
				<div class="clearfix"></div>