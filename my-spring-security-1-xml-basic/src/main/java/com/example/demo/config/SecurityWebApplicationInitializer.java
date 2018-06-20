package com.example.demo.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// ~Initializer 클래스는 스프링이 자동으로 인식한다. 즉, 스캐닝 대상으로 삼기위한 @Component 설정이 필요 없다.
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	
//	<filter>
//		<filter-name>springSecurityFilterChain</filter-name>
//		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
//	</filter>
//	
//	<filter-mapping>
//		<filter-name>springSecurityFilterChain</filter-name>
//		<url-pattern>/*</url-pattern>
//		<!-- Only when the request is being processed with the error page mechanism -->
//		<dispatcher>ERROR</dispatcher>
//		<!-- Only when the request comes directly from the client -->
//		<dispatcher>REQUEST</dispatcher>
//	</filter-mapping>

}
