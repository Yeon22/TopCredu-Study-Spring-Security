<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring Security Example</title>
<style type="text/css">
.error, .msg {
	display: inline-block;
	padding: 8px;
	margin-bottom: 8px;
	border: 1px solid silver;
}
</style>
</head>
<body>

	<h2>${title }</h2>

	<c:if test="${not empty error}">
		<div class="error">${error}</div>
	</c:if>
	<c:if test="${not empty msg}">
		<div class="msg">${msg}</div>
	</c:if>

	<!-- 세션에 저장한 targetUrl 값을 사용한다. -->
	<form:form action="/login?targetUrl=${targetUrl}" method="post">
		<table>
			<tr>
				<td>ID:</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password"></td>
			</tr>
			<!-- 리멤버미 로그인 > 어드민 컨텐츠 접근 > 계정 재 검증 과정에서는 체크박스를 노출할 필요가 없다. -->
			<c:if test="${empty loginRetest}">
			<tr>
				<td></td>
				<td><input type="checkbox" name="remember-me" />Remember Me</td>
			</tr>
			</c:if>
			<tr>
				<td colspan="2"><input type="submit" value="Login" /></td>
			</tr>
		</table>
		<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
	</form:form>

</body>
</html>