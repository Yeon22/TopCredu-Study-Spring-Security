Spring Security

1. 오토 로그인(Remember-me) 설정

1.1. XML 설정

security.xml

```
	<http auto-config="true" use-expressions="true">
		<!-- 
			token-validity-seconds="1209600" : 보존기간을 2주로 설정한다.
			remember-me-parameter="remember-me" : 체크박스의 속성 name에 설정할 키값
			data-source-ref : 이 속성이 명시되면, "Persistent Token Approach" 방법이 활성화 된다. 
			기본값은 "Simple Hash-Based Token Approach" 방법이다.
		 -->
		<remember-me token-validity-seconds="1209600" remember-me-parameter="remember-me" data-source-ref="dataSource" />
	</http>
```

1.2. Java-config 설정

com.example.demo.config.SecurityConfigurationJava

```
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.rememberMe()
			.tokenValiditySeconds(1209600)
			.rememberMeParameter("remember-me")
			.tokenRepository(persistentTokenRepository());
	}
	
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}
```

remember-me를 위해서 persistent_logins 테이블이 필요하다.

2. 리멤버미 화면연동

login.jsp

```
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
	</form:form>
```

security.xml

```
	<http auto-config="true" use-expressions="true">
		<form-login login-page="/login" 
			login-processing-url="/login"
			username-parameter="username"
			password-parameter="password"
			authentication-failure-handler-ref="myAuthenticationFailureHandler"
			authentication-success-handler-ref="myAuthenticationSuccessHandler" />
	</http>
```

targetUrl 정보는 로그인 정보 POST 시 인증/권한체크를 거쳐서 authentication-success-handler-ref이 가리키는 빈 myAuthenticationSuccessHandler 핸들러의 onAuthenticationSuccess 메소드에서 확인한다. 로그인 성공 후 연동 할 URL을 뷰에서 결정할 수 있는 방법이다.
