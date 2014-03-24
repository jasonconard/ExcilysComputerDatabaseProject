<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="db" tagdir="/WEB-INF/tags/dashboard" %>

<section id="main">
	<h1 id="homeTitle">${page.number} Computers found</h1>
	<div id="actions">
		<form action="" method="GET">
			<input type="search" id="searchbox" name="search" value="${page.filter}"
				placeholder="Search name"> <input type="submit"
				id="searchsubmit" value="Filter by name" class="btn btn-primary">
		</form>
		<a class="btn btn-success" id="add" href="AddComputer">Add
			Computer</a>
	</div>
	<br />
	<c:if test="${message != null}">
		<p class="alert alert-success">${message} <br/></p>
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
					<td>${entry.company.name}</td>
					<td>
						<a href="EditComputer?computerId=${entry.id}"><span class="glyphicon glyphicon-pencil"></span></a> 
						<a href="DashBoard?computerId=${entry.id}&delete=delete&page=${lastPage}&search=${page.filter}&order=${page.column}&dir=${page.direction}"
						onclick="return confirm('Are you sure to delete?');">
							<span class="glyphicon glyphicon-trash"></span>
						</a>
					</td>

				</tr>
			</c:forEach>
			<db:paginationTR/>
		</tbody>
	</table>
</section>

<jsp:include page="include/footer.jsp" />
