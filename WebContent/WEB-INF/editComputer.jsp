<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section id="main">

	<h1>Edit Computer</h1>
	<form action="EditComputer" class="form-inline" method="POST" id="editComputerForm">
		<input type="hidden" name="computerId" value="${computer.id}"/>
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" class="form-control text-form" name="name" width="20" value="${computer.name}"
						   data-validation="length" data-validation-length="1-255" data-validation-optional="false"
						   data-validation-error-msg="The user name has to be a value between 1-255 characters"/>
					<span class="help-inline">Required</span>
				</div>
			</div>
			<br/>
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" class="form-control date-form" name="introduced" value="${computer.introduced}"
							data-validation="date" data-validation-format="yyyy-mm-dd" data-validation-optional="true"
							data-validation-error-msg="You have to write a correct date in the format YYYY-MM-dd (if you have a recent browser, it could maybe use your nationality format, no problem)"
					/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<br/>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" class="form-control date-form" name="discontinued" value="${computer.discontinued}"
							data-validation="date" data-validation-format="yyyy-mm-dd" data-validation-optional="true"
							data-validation-error-msg="You have to write a correct date in the format YYYY-MM-dd (if you have a recent browser, it could maybe use your nationality format, no problem)"
					/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<br/>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company" class="form-control select-form">
						<option value="0">--</option>
						<c:forEach var="entry" items="${allCompany}">
							<c:if test="${computer.company.id == entry.id}">
								<option value="${entry.id}" selected="selected">${entry.name}</option>
							</c:if>
							<c:if test="${computer.company.id != entry.id}">
								<option value="${entry.id}">${entry.name}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
			<br/>
		</fieldset>
		<div class="actions">
			<input type="submit" value="Edit" class="btn btn-primary">
			or <a href="DashBoard" class="btn btn-default">Cancel</a>
		</div>
	</form>
	
	<br/>
	<c:if test="${message != null}">
		<p class="alert alert-success">${message} <br/> Do you want to return into the main <a href="DashBoard">page</a>?</p>
	</c:if>
	<c:if test="${error != null}">
		<p class="alert alert-danger">${error} <br/> Do you want to return into the main <a href="DashBoard">page</a>?</p>
	</c:if>
</section>

<script src="js/jquery.form-validator.min.js"></script>
<script> $.validate(); </script>

<jsp:include page="include/footer.jsp" />