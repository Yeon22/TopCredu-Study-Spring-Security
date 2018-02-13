Spring Security

1. 시큐리티 설정은 XML 파일에 한다.
resources/spring/security.xml

2. 사용자 계정정보는 XML 파일에 한다.
resources/spring/security.xml

3. 로그인 화면은 스프링 시큐리티가 제공하는 것을 그대로 사용하고 있다.
관련한 설정을 하지 않으면 기본적으로 스프링 시큐리티가 제공하는 화면을 사용한다.

4. 시큐리티 필터체인 등록

4.1 web.xml 파일을 사용하는 경우 다음과 같이 한다.
```
	<filter>
		<filter-name>springSecurityFilterChain</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
	</filter>
	
	<filter-mapping>
		<filter-name>springSecurityFilterChain</filter-name>
		<url-pattern>/*</url-pattern>
		<!-- Only when the request is being processed with the error page mechanism -->
		<dispatcher>ERROR</dispatcher>
		<!-- Only when the request comes directly from the client -->
		<dispatcher>REQUEST</dispatcher>
	</filter-mapping>
```

4.2 web.xml 파일을 사용하지 않고 Java-config 설정으로 하는 경우 다음과 같이 한다.
```
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	// nothing to do. it's done.
}
```
