<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<section id="main">

	<h1><spring:message code="view.addComputer.title" text="Add Computer" /></h1>
	<form:form action="AddComputer" class="form-inline" id="addComputerForm" method="POST" modelAttribute="dto">
		<fieldset>
			<div class="clearfix">
				<label for="name"><spring:message code="view.addComputer.computerName" text="Computer name" /> :</label>
				<div class="input">
					<spring:message code="view.addComputer.validationName" var="validationNameMessage"/>
					<form:input type="text" class="form-control text-form" path="name" name="name"
						width="20" data-validation="length"
						data-validation-length="1-255" data-validation-optional="false"
						data-validation-error-msg="${validationNameMessage}" />
					<span class="help-inline"><spring:message code="view.addComputer.required" text="Required" /></span>
				</div>
			</div>
			<br />
			<div class="clearfix">
				<label for="introduced"><spring:message code="view.addComputer.introduced" text="Introduced Date" /> :</label>
				<div class="input">
					<spring:message code="view.addComputer.validationDate" var="validationDate"/>
					<spring:message code="view.addComputer.dateFormat" var="dateFormat"/>
					<form:input type="date" class="form-control date-form" name="introduced" path="introduced"
						data-validation="date"
						data-validation-format="${dateFormat}"
						data-validation-optional="true"
						data-validation-error-msg="${validationDate}" />
					<span class="help-inline">${dateFormat}</span>
				</div>
			</div>
			<br />
			<div class="clearfix">
				<label for="discontinued"><spring:message code="view.addComputer.discontinued" text="Discontinued Date" /> :</label>
				<div class="input">
					<form:input type="date" class="form-control date-form" path="discontinued"
						name="discontinued" data-validation="date"
						data-validation-format="${dateFormat}"
						data-validation-optional="true"
						data-validation-error-msg="${validationDate}" />
					<span class="help-inline">${dateFormat}</span>
				</div>
			</div>
			<br />
			<div class="clearfix">
				<label for="company"><spring:message code="view.addComputer.companyName" text="Company Name" /> :</label>
				<div class="input">
					<select name="company" class="form-control select-form">
						<option value="0">--</option>
						<c:forEach var="entry" items="${allCompany}">
							<c:if test="${dto.companyId == entry.id}">
								<option value="${entry.id}" selected="selected">${entry.name}</option>
							</c:if>
							<c:if test="${dto.companyId != entry.id}">
								<option value="${entry.id}">${entry.name}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
			<br />
		</fieldset>
		<div class="actions">
			<input type="submit" value="Add" class="btn btn-primary"> <spring:message code="view.addComputer.or" text="or" />
			<a href="DashBoard" class="btn btn-default"><spring:message code="view.addComputer.cancel" text="Cancel" /></a>
		</div>
		<hr/>
		<c:if test="${error}">
			<div class="panel panel-danger">
				<div class="panel-heading">
					<spring:message code="view.addComputer.errorsReport" text="Error(s) reporting" /> :
				</div>
				<div class="panel-body">
					<form:errors path="*"/>
				</div>
			</div>
		</c:if>
	</form:form>
</section>

<script src="js/jquery.form-validator.min.js"></script>
<script>
	$.validate();
</script>

<jsp:include page="include/footer.jsp" />