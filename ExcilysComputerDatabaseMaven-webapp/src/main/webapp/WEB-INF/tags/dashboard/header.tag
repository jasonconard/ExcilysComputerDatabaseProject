<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<thead>
	<tr>
		<c:if test="${column=='name' && direction=='DESC'}">
			<th class="col2"><spring:message code="view.dashboard.tag.header.computerName" text="Computer Name" />
				<cm:url servlet="DashBoard" pageNo="1" search="${filter}"
								order="name" dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(column=='name' && direction=='DESC')}">
			<th class="col2"><spring:message code="view.dashboard.tag.header.computerName" text="Computer Name" />
				<cm:url servlet="DashBoard" pageNo="1" search="${filter}"
						order="name" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		
		
		<c:if test="${column=='introduced' && direction=='DESC'}">
			<th class="col3"><spring:message code="view.dashboard.tag.header.introduced" text="Introduced Date" />
				<cm:url servlet="DashBoard" pageNo="1" search="${filter}"
								order="introduced" dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(column=='introduced' && direction=='DESC')}">
			<th class="col3"><spring:message code="view.dashboard.tag.header.introduced" text="Introduced Date" />
				<cm:url servlet="DashBoard" pageNo="1" search="${filter}"
						order="introduced" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		
		
		<c:if test="${column=='discontinued' && direction=='DESC'}">
			<th class="col4"><spring:message code="view.dashboard.tag.header.discontinued" text="Discontinued Date" />
				<cm:url servlet="DashBoard" pageNo="1" search="${filter}"
								order="discontinued" dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(column=='discontinued' && direction=='DESC')}">
			<th class="col4"><spring:message code="view.dashboard.tag.header.discontinued" text="Discontinued Date" />
				<cm:url servlet="DashBoard" pageNo="1" search="${filter}"
						order="discontinued" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		
		
		<c:if test="${column=='company.name' && direction=='DESC'}">
			<th class="col5"><spring:message code="view.dashboard.tag.header.companyName" text="Company Name" />
				<cm:url servlet="DashBoard" pageNo="1" search="${filter}"
								order="company.name"	dir="ASC"
								icon="glyphicon glyphicon-arrow-up"/>
			</th>
		</c:if>
		<c:if test="${!(column=='company.name' && direction=='DESC')}">
			<th class="col5"><spring:message code="view.dashboard.tag.header.companyName" text="Company Name" />
				<cm:url servlet="DashBoard" pageNo="1" search="${filter}"
						order="company.name" dir="DESC"
						icon="glyphicon glyphicon-arrow-down"/>
			</th>
		</c:if>
		<th class="col6"><spring:message code="view.dashboard.tag.header.actions" text="Actions" /></th>
	</tr>
</thead>