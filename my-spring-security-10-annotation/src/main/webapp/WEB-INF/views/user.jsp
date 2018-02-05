<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring Security Example</title>
</head>
<body>

	<h2>${title}</h2>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h3>
			<sec:authentication property="principal.username"/> 
			<sec:authentication property="principal.authorities"/>
		</h3>
	</c:if>

</body>
</html>