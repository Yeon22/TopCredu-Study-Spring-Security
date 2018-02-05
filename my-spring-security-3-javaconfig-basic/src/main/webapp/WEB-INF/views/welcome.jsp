<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring Security Example</title>
<style type="text/css">
.box {
	display: inline-block;
	padding: 8px;
	margin-bottom: 8px;
	border: 1px solid silver;
}
</style>
</head>
<body>

	<h2>${title}</h2>

	<div class="box">
		<ul>
			<li><a href="<c:url value='/admin' />">admin</a></li>
			<li><a href="<c:url value='/dba' />">dba</a></li>
		</ul>
	</div>
	
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h3>
			<sec:authentication property="principal.username"/> 
			<sec:authentication property="principal.authorities"/>
		</h3>
		
		<a href="javascript:logoutSubmit()">Logout</a>
	</c:if>
	
	<form action="logout" method="post" id="logoutForm" style="display:none">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	<script>
		function logoutSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
	
</body>
</html>