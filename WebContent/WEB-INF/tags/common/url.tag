<%@ tag body-content="empty" dynamic-attributes="dynattrs" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="servlet" required="true" %>
<%@ attribute name="computerId" required="false" %>
<%@ attribute name="pageNo" required="false" %>
<%@ attribute name="search" required="false" %>
<%@ attribute name="order" required="false" %>
<%@ attribute name="dir" required="false" %>
<%@ attribute name="icon" required="false" %>
<%@ attribute name="delete" required="false" %>
<%@ attribute name="myOnClick" required="false" %>

<a href="${servlet}?computerId=${computerId}&delete=${delete}&page=${pageNo}&search=${search}&order=${order}&dir=${dir}" onclick="${myOnClick}">
	<span class="${icon}"></span>
</a>