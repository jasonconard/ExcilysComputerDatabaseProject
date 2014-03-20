<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<thead>
	<tr>
		<!-- Variable declarations for passing labels as parameters -->
		<!-- Table header for Computer Name -->
		<c:if test="${page.column=='name' && page.direction=='DESC'}">
			<th class="col2">Computer Name
				<a href="DashBoard?page=1&search=${page.filter}&order=name&dir=ASC"><span class="glyphicon glyphicon-arrow-up"></span></a>
			</th>
		</c:if>
		<c:if test="${!(page.column=='name' && page.direction=='DESC')}">
			<th class="col2">Computer Name
				<a href="DashBoard?page=1&search=${page.filter}&order=name&dir=DESC"><span class="glyphicon glyphicon-arrow-down"></span></a>
			</th>
		</c:if>
		<c:if test="${page.column=='introduced' && page.direction=='DESC'}">
			<th class="col3">Introduced Date
				<a href="DashBoard?page=1&search=${page.filter}&order=introduced&dir=ASC"><span class="glyphicon glyphicon-arrow-up"></span></a>
			</th>
		</c:if>
		<c:if test="${!(page.column=='introduced' && page.direction=='DESC')}">
			<th class="col3">Introduced Date
				<a href="DashBoard?page=1&search=${page.filter}&order=introduced&dir=DESC"><span class="glyphicon glyphicon-arrow-down"></span></a>
			</th>
		</c:if>
		<c:if test="${page.column=='discontinued' && page.direction=='DESC'}">
			<th class="col4">Discontinued Date
				<a href="DashBoard?page=1&search=${page.filter}&order=discontinued&dir=ASC"><span class="glyphicon glyphicon-arrow-up"></span></a>
			</th>
		</c:if>
		<c:if test="${!(page.column=='discontinued' && page.direction=='DESC')}">
			<th class="col4">Discontinued Date
				<a href="DashBoard?page=1&search=${page.filter}&order=discontinued&dir=DESC"><span class="glyphicon glyphicon-arrow-down"></span></a>
			</th>
		</c:if>
		<c:if test="${page.column=='company' && page.direction=='DESC'}">
			<th class="col5">Company
				<a href="DashBoard?page=1&search=${page.filter}&order=company&dir=ASC"><span class="glyphicon glyphicon-arrow-up"></span></a>
			</th>
		</c:if>
		<c:if test="${!(page.column=='company' && page.direction=='DESC')}">
			<th class="col5">Company
				<a href="DashBoard?page=1&search=${page.filter}&order=company&dir=DESC"><span class="glyphicon glyphicon-arrow-down"></span></a>
			</th>
		</c:if>
		<th class="col6">Actions</th>
	</tr>
</thead>