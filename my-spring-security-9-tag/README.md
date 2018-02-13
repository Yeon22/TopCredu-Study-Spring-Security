Spring Security

1. JSP에서 사용할 수 있는 스프링 시큐리티 태그

pom.xml

```
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-taglibs</artifactId>
		</dependency>
```

home.jsp

```
	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<sec:authorize access="isAnonymous()">
	</sec:authorize>
	
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal.username"/> 
		<sec:authentication property="principal.authorities"/>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_USER')">
	</sec:authorize>
	
	<sec:authorize url="/user">
	</sec:authorize>
```