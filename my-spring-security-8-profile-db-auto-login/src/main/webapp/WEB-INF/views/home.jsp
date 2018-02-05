<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring Security</title>
</head>
<body>

	<h2>${title }</h2>
	
	<div>
		<ul>
			<li><a href="<c:url value='/'/>">home</a></li>
			<li><a href="<c:url value='/home'/>">home</a></li>
			<li><a href="<c:url value='/user'/>">user</a></li>
			<li><a href="<c:url value='/manager'/>">manager</a></li>
			<li><a href="<c:url value='/admin'/>">admin</a></li>
		</ul>
	</div>

	<c:if test="${pageContext.request.userPrincipal.name == null}">
		<a href="<c:url value='/login'/>">Login</a>
	</c:if>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h3>
			<sec:authentication property="principal.username"/> 
			<sec:authentication property="principal.authorities"/>
		</h3>
		
		<h5>
			<sec:authorize access="isRememberMe()">
				Login done by "Remember Me".
			</sec:authorize>
			<sec:authorize access="isFullyAuthenticated()">
				Login done by "Fully Authenticated (username/password)".
			</sec:authorize>
		</h5>
		
		<a href="javascript:logoutSubmit()">Logout</a>
	</c:if>

	<form:form action="/logout" method="post" id="logoutForm" style="display:none">
		<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
	</form:form>
	<script>
		function logoutSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

</body>
</html>