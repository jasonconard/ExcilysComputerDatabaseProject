<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<tr>
	<td colspan="4" style="vertical-align:middle;">
			<c:if test="${lastPage!=-1}">
				<cm:url servlet="DashBoard" 
								pageNo="0"
								search="${filter}"
								order="${column}"
								dir="${direction}"
								icon="glyphicon glyphicon-fast-backward"/>
				<cm:url servlet="DashBoard" 
								pageNo="${lastPage}"
								search="${filter}"
								order="${column}"
								dir="${direction}"
								icon="glyphicon glyphicon-backward"/>
			</c:if>
			<c:if test="${lastPage==-1}">
				<span class="unclickable glyphicon glyphicon-fast-backward"></span>
				<span class="unclickable glyphicon glyphicon-backward"></span>
			</c:if>
			Page(${page.number}/${page.totalPages-1}) 
			<c:if test="${nextPage!=-1}">
				<cm:url servlet="DashBoard" 
								pageNo="${nextPage}"
								search="${filter}"
								order="${column}"
								dir="${direction}"
								icon="glyphicon glyphicon-forward"/>
				<cm:url servlet="DashBoard" 
								pageNo="${page.totalPages-1}"
								search="${filter}"
								order="${column}"
								dir="${direction}"
								icon="glyphicon glyphicon-fast-forward"/>
			</c:if>
			<c:if test="${nextPage==-1}">
				<span class="unclickable glyphicon glyphicon-forward"></span>
				<span class="unclickable glyphicon glyphicon-fast-forward"></span>
			</c:if>
	</td>
	<td>
		<spring:message code="view.dashboard.tag.paginationTR.goto" var="goTo"/>
		<form action="" class="form-inline" method="GET">
			<input type="number" id="searchbox" name="page" value="${numero}" placeholder="n°" min="1" max="${page.totalPages-1}"/> / ${page.totalPages-1} &nbsp;&nbsp;
			<input type="submit" id="pagesubmit" value="${goTo}" class="btn btn-primary btn-xs"/>
			<input type="hidden" id="hiddenSearch" name="search" value="${filter}"/>
			<input type="hidden" id="hiddenOrder" name="order" value="${column}"/>
			<input type="hidden" id="hiddenDir" name="dir" value="${direction}"/>
		</form>				
	</td>
</tr>