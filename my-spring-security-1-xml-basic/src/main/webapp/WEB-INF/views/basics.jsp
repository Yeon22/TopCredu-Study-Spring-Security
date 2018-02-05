<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Security Example</title>
</head>
<body>

	<h2>${title}</h2>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h3>${pageContext.request.userPrincipal.name}</h3>
		<c:forEach items="${authorities }" var="authority">
			<h5><c:out value="${authority }"/></h5>
		</c:forEach>
		
		<%-- 시큐리티 3 버전에서는 logout 대신 j_spring_security_logout을 사용한다. --%>
		<form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="post" >
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="submit" value="Logout" />
		</form>
	</c:if>
	
	<hr>
	
	<%
		Object obj = request.getAttribute("principal");
	
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(obj);
		out.print(json);
	%>
	<!-- 
		{
		  "authorities": [
		    {
		      "authority": "ROLE_USER"
		    }
		  ],
		  "details": {
		    "remoteAddress": "0:0:0:0:0:0:0:1",
		    "sessionId": "2903C523AF6B5BCE7C346ADC1EEB1C05"
		  },
		  "authenticated": true,
		  "principal": {
		    "password": null,
		    "username": "gildong",
		    "authorities": [
		      {
		        "authority": "ROLE_USER"
		      }
		    ],
		    "accountNonExpired": true,
		    "accountNonLocked": true,
		    "credentialsNonExpired": true,
		    "enabled": true
		  },
		  "credentials": null,
		  "name": "gildong"
		}
	 -->
</body>
</html>