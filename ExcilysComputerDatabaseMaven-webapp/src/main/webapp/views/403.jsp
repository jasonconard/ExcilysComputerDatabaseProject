<jsp:include page="include/header.jsp" />
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<br />
<br />
<br />
<br />
<br />
<br />
<br />

<table style="width: 100%">
	<tr>
		<td style="text-align: right">
			<a href="DashBoard">
				<img class="beautifulImg" style="width: 400px; height: 400px;" src="img/403.jpg" />
			</a>
		</td>
		<td style="text-align: center">
		
			<span style="font-size: 14px; font-family: Candara, sans-serif;"> 
				<spring:message code="view.403.quote" text="no text found" />
			</span> 
			<div style="margin: 0; padding: 0; text-align: center;">
					<span style="font-size: 12px;">
						 - 
						<em><spring:message code="view.403.ref" text="no text found" /></em>
						 -
					</span>
			</div>
		</td>
	</tr>
</table>

<jsp:include page="include/footer.jsp" />
