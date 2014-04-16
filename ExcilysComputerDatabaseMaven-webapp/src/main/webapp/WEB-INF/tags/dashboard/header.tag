<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<thead>
	<tr>
		<c:if test="${page.column=='cu.name' && page.direction=='DESC'}">
			<th class="col2"><spring:message code="view.dashboard.tag.header.computerName" text="Computer Name" />
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
								order="cu.name" dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(page.column=='cu.name' && page.direction=='DESC')}">
			<th class="col2"><spring:message code="view.dashboard.tag.header.computerName" text="Computer Name" />
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
						order="cu.name" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		
		
		<c:if test="${page.column=='introduced' && page.direction=='DESC'}">
			<th class="col3"><spring:message code="view.dashboard.tag.header.introduced" text="Introduced Date" />
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
								order="introduced" dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(page.column=='introduced' && page.direction=='DESC')}">
			<th class="col3"><spring:message code="view.dashboard.tag.header.introduced" text="Introduced Date" />
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
						order="introduced" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		
		
		<c:if test="${page.column=='discontinued' && page.direction=='DESC'}">
			<th class="col4"><spring:message code="view.dashboard.tag.header.discontinued" text="Discontinued Date" />
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
								order="discontinued" dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(page.column=='discontinued' && page.direction=='DESC')}">
			<th class="col4"><spring:message code="view.dashboard.tag.header.discontinued" text="Discontinued Date" />
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
						order="discontinued" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		
		
		<c:if test="${page.column=='ca.name' && page.direction=='DESC'}">
			<th class="col5"><spring:message code="view.dashboard.tag.header.companyName" text="Company Name" />
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
								order="ca.name"	dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(page.column=='ca.name' && page.direction=='DESC')}">
			<th class="col5"><spring:message code="view.dashboard.tag.header.companyName" text="Company Name" />
				<cm:url servlet="DashBoard" pageNo="1" search="${page.filter}"
						order="ca.name" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		<th class="col6"><spring:message code="view.dashboard.tag.header.actions" text="Actions" /></th>
	</tr>
</thead>