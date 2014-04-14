<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="db" tagdir="/WEB-INF/tags/dashboard" %>
<%@ taglib prefix="cm" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<section id="main">
	<h1 id="homeTitle">${page.number} <spring:message code="view.dashboard.computerFound" text="Computer(s) Found" /></h1>
	<div id="actions">
		<form action="" method="GET">
			<spring:message code="view.dashboard.filterByName" var="filterByName" />
			<spring:message code="view.dahsboard.searchName" var="searchName" />
			<input type="search" id="searchbox" name="search" value="${page.filter}"
				placeholder="${searchName}"> <input type="submit"	id="searchsubmit" value="${filterByName}" class="btn btn-primary">
		</form>
		<a class="btn btn-success" id="add" href="AddComputer"><spring:message code="view.dashboard.addComputer" text="Add computer" /></a>
	</div>
	<br />
	<c:if test="${message != null}">
		<p class="alert alert-success">${message} <br/></p>
	</c:if>
	<c:if test="${error != null}">
		<p class="alert alert-danger">${error} <br/></p>
	</c:if>
	<table class="computers table table-striped table-bordered">
		<db:header/>
		<tbody>
			<db:paginationTR/>
			<c:forEach var="entry" items="${page.listElement}">
				<tr>
					<td>${entry.name}</td>
					<td>${entry.introduced}</td>
					<td>${entry.discontinued}</td>
					<td>${entry.companyName}</td>
					<td>
						<cm:url servlet="EditComputer" 
								computerId="${entry.id}"
								icon="glyphicon glyphicon-pencil"/>
						<spring:message code="view.dashboard.delete" var="deleteMessage"/>
						<cm:url servlet="DashBoard" 
								computerId="${entry.id}"
								delete="delete"
								pageNo="${lastPage}"
								search="${page.filter}"
								order="${page.column}"
								dir="${page.direction}"
								icon="glyphicon glyphicon-trash"
								myOnClick="return confirm('${deleteMessage}');"/>
					</td>

				</tr>
			</c:forEach>
			<db:paginationTR/>
		</tbody>
	</table>
</section>

<jsp:include page="include/footer.jsp" />
