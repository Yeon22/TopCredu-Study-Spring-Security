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
			<li><a href="<c:url value='/home'/>">home</a></li>
			<sec:authorize access="isAnonymous()">
				<li><a href="<c:url value='/login'/>">Login</a></li>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<li><a href="javascript:logoutSubmit()">Logout</a></li>
				<li><a href="<c:url value='/user'/>">user</a></li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')">
				<li><a href="<c:url value='/manager'/>">manager</a></li>
			</sec:authorize>
			<sec:authorize url="/admin">
				<li><a href="<c:url value='/admin'/>">admin</a></li>
			</sec:authorize>
		</ul>
	</div>
	
	<sec:authorize access="isAuthenticated()">
		<h3>
			<sec:authentication property="principal.username"/> 
			<sec:authentication property="principal.authorities"/>
		</h3>
	</sec:authorize>
	
	<form action="logout" method="post" id="logoutForm" style="display:none">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	<script>
		function logoutSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
	
	<hr>
	
	<h3>@Secured, @RolesAllowed, @PreAuthorize</h3>
	<div class="box">
		<ul>
			<li><a href="<c:url value='/members/all'/>">/members/all</a></li>
			<li><a href="<c:url value='/members/all/size'/>">/members/all/size</a></li>
			<li><a href="<c:url value='/members/insert'/>">/members/insert</a></li>
			<li><a href="<c:url value='/members/delete/1'/>">/members/delete/1</a></li>
		</ul>
	</div>
	
	<hr>
	
	<h3>@PreFilter, @PostFilter, @PreAuthorize, @PostAuthorize</h3>
	<div class="box">
		<ul>
			<li><a href="<c:url value='/boards/testPreFilter'/>">/testPreFilter</a></li>
			<li><a href="<c:url value='/boards/testPostFilter'/>">/testPostFilter</a></li>
			<li><a href="<c:url value='/boards/testPreAuthorize'/>">/testPreAuthorize</a></li>
			<li><a href="<c:url value='/boards/testPostAuthorize'/>">/testPostAuthorize</a></li>
		</ul>
	</div>
	
</body>
</html>