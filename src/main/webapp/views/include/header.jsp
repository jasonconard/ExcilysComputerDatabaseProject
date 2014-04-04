<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<title>EPF Computer Database</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link href="css/bootstrap.css" rel="stylesheet" media="screen">
		<link href="css/main.css" rel="stylesheet" media="screen">
		<link href="css/main_light.css" rel="stylesheet" media="screen" class="switchable">
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
								<a href="DashBoard"><spring:message code="view.header.title" text="Application - Computer Database" /></a>
							</h1>
						</div>
					</td>
					<td id="colHeader2">
						<div id="cssSwitch">
							<spring:message code="view.header.style.light" var="skinLight" />
							<spring:message code="view.header.style.dark" var="skinDark" />
							<spring:message code="view.header.style.darkGreen" var="skinDarkGreen" />
							<spring:message code="view.header.style.littlePony" var="skinLittlePony" />
							<spring:message code="view.header.style.ice" var="skinIce" />
							<spring:message code="view.header.style.thomas" var="skinThomas" />
							<a href="#" rel="css/main_light.css" class="csslight" title="${skinLight}"><img src="img/lightSkinButton.png"/></a>
							<a href="#" rel="css/main_dark.css" class="cssdark" title="${skinDark}"><img src="img/darkSkinButton.png"/></a>
							<a href="#" rel="css/main_dark_green.css" class="cssdg" title="${skinDarkGreen}"><img src="img/darkGreenSkinButton.png"/></a>
							<a href="#" rel="css/main_little_pony.css" class="csslp" title="${skinLittlePony}"><img src="img/littlePonySkinButton.png"/></a>
							<a href="#" rel="css/main_ice.css" class="cssice" title="${skinIce}"><img src="img/iceSkinButton.png"/></a>
							<a href="#" rel="css/main_thomas.css" class="csstho" title="${skinThomas}"><img src="img/thomasSkinButton.png"/></a>
						</div>
					</td>
					<td id="colHeader3">
						<div id="webSiteIcon"></div>
						<a href="?language=en_EN"><img class="beautifulImg" src="img/flag_english.png"/></a>|<a href="?language=fr_FR"><img class="beautifulImg" src="img/flag_france.png"/></a>
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