<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring Security Example</title>
<style type="text/css">
.box {
	display: inline-block;
	padding: 8px;
	padding-right: 40px;
	margin-bottom: 8px;
	border: 1px solid silver;
}
</style>
</head>
<body>

	<h2>${title}</h2>

	<div class="box">
		<ul>
			<li><a href="<c:url value='/'/>">home</a></li>
			<li><a href="<c:url value='/home'/>">home</a></li>
			<li><a href="<c:url value='/user'/>">user</a></li>
			<li><a href="<c:url value='/manager'/>">manager</a></li>
			<li><a href="<c:url value='/admin'/>">admin</a></li>
		</ul>
	</div>
	
	<c:if test="${pageContext.request.userPrincipal.name == null}">
		<div>
			<a href="<c:url value='/login'/>">Login</a>
		</div>
	</c:if>
	
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h3>
			<sec:authentication property="principal.username"/> 
			<sec:authentication property="principal.authorities"/>
		</h3>
		
		<div>
			<a href="javascript:logoutSubmit()">Logout</a>
		</div>
	</c:if>
	
	<form action="logout" method="post" id="logoutForm" style="display:none">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	<script>
		function logoutSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
	
	<hr>
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<h5>You are an Administrator.</h5>
	</sec:authorize>

	<sec:authorize access="hasRole('ROLE_MANAGER')">
		<h5>You are a Manager.</h5>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_USER')">
		<h5>You are an User.</h5>
	</sec:authorize>
	
	<sec:authorize access="isAnonymous()">
		<h5>You are an Anonymous.</h5>
	</sec:authorize>
	
	<hr>
	
	<sec:authorize access="isAuthenticated()">
		<h3>
			<sec:authentication property="principal.username"/> 
			<sec:authentication property="principal.authorities"/>
		</h3>
		
		<c:url var="logoutUrl" value="logout" />
		<form:form action="${logoutUrl }" method="post">
			<button type="submit">Logout</button>
		</form:form>
	</sec:authorize>
	
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal.authorities" var="authorities"/>
		<c:forEach items="${authorities }" var="authority">
			<h5><mark>${authority }</mark></h5>
		</c:forEach>
	</sec:authorize>
	
	<div class="box">
		<ul>
			<li><a href="<c:url value='/home'/>">home</a></li>
			<sec:authorize url="/user">
				<li><a href="<c:url value='/user'/>">user</a></li>
			</sec:authorize>
			<sec:authorize url="/manager">
				<li><a href="<c:url value='/manager'/>">manager</a></li>
			</sec:authorize>
			<sec:authorize url="/admin">
				<li><a href="<c:url value='/admin'/>">admin</a></li>
			</sec:authorize>
		</ul>
	</div>
	
</body>
</html>