<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" tagdir="/WEB-INF/tags/common" %>

<thead>
	<tr>
		<c:if test="${page.column=='name' && page.direction=='DESC'}">
			<th class="col2">Computer Name
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
								order="name" dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(page.column=='name' && page.direction=='DESC')}">
			<th class="col2">Computer Name
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
						order="name" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		
		
		<c:if test="${page.column=='introduced' && page.direction=='DESC'}">
			<th class="col3">Introduced Date
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
								order="introduced" dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(page.column=='introduced' && page.direction=='DESC')}">
			<th class="col3">Introduced Date
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
						order="introduced" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		
		
		<c:if test="${page.column=='discontinued' && page.direction=='DESC'}">
			<th class="col4">Discontinued Date
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
								order="discontinued" dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(page.column=='discontinued' && page.direction=='DESC')}">
			<th class="col4">Discontinued Date
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
						order="discontinued" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		
		
		<c:if test="${page.column=='company' && page.direction=='DESC'}">
			<th class="col5">Company
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
								order="company"	dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(page.column=='company' && page.direction=='DESC')}">
			<th class="col5">Company
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
						order="company" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		<th class="col6">Actions</th>
	</tr>
</thead>