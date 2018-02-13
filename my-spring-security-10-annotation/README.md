Spring Security

1. JSP에서 사용할 수 있는 스프링 시큐리티 태그

pom.xml

```
	<!-- for @RolesAllowed -->
	<dependency>
		<groupId>javax.annotation</groupId>
		<artifactId>jsr250-api</artifactId>
		<version>1.0</version>
	</dependency>
```

2. Annotation 활성화

2.1. Java-config 설정

@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true, jsr250Enabled=true)

2.2. XML 설정

<security:global-method-security pre-post-annotations="enabled" secured-annotations="enabled" jsr250-annotations="enabled" />

3. 속성 설정에 따라 사용할 수 잇는 애노테이션 종류

pre-post-annotations : 
	@PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter
	
secured-annotations : 
	@Secured
	
jsr250-annotations : 
	@RolesAllowed

4. 사용 예
com.example.demo.sample.service.BoardService
com.example.demo.sample.service.MemberService
