<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>EPF Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen" class="switchable">
</head>

<script src="js/jquery.min.js"></script>
<script src="js/jquery.cookie.js"></script>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<table style="width:100%;">
			<tr>
				<td id="colHeader1">
					<div id="myLittleTitle">
						<h1 class="fill">
							<a href="DashBoard"> Application - Computer Database </a>
						</h1>
					</div>
				</td>
				<td id="colHeader2">
					<div id="cssSwitch">
						<a href="#" rel="css/main.css" class="csslight" title="Light Skin"><img src="img/lightSkinButton.png"/></a>
						<a href="#" rel="css/main_dark.css" class="cssdark" title="Dark Skin"><img src="img/darkSkinButton.png"/></a>
						<a href="#" rel="css/main_dark_green.css" class="cssdg" title="Dark Green Skin"><img src="img/darkGreenSkinButton.png"/></a>
						<a href="#" rel="css/main_little_pony.css" class="csslp" title="Little Pony Skin"><img src="img/littlePonySkinButton.png"/></a>
						<a href="#" rel="css/main_ice.css" class="cssice" title="Ice Skin"><img src="img/iceSkinButton.png"/></a>
					</div>
				</td>
				<td id="colHeader3">
					<div id="webSiteIcon"></div>
				</td>
			</tr>
		</table>
	</header>

	<script type="text/javascript">
		if($.cookie("css")) {
		    $("link.switchable").attr("href",$.cookie("css"));
		}
	
		$(document).ready(function() {
		    $("#cssSwitch a").click(function() { 
		        $("link.switchable").attr("href",$(this).attr('rel'));
		        $.cookie('css',$(this).attr('rel'), { expires: 30, path: '/' });
		        //$('body').hide().fadeIn(200);
		        return false;
		    });
		});
	</script>