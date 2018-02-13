Spring Security

1. 시큐리티 설정은 Java 파일에 한다.
com.example.demo.config.MySecurityConfiguration

```
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/dba/**").access("hasAnyRole('ROLE_ADMIN','ROLE_DBA')")
			.and().formLogin() 
			.loginPage("/login") // 커스텀 로그인 페이지 활성화 > 컨트롤러 추가 > JSP 추가
			.defaultSuccessUrl("/welcome")
			.failureUrl("/login?error")
			.usernameParameter("username")
			.passwordParameter("password")
			.and().logout().logoutSuccessUrl("/login?logout")
			.and().exceptionHandling().accessDeniedPage("/login/denied");
	}
```

2. 사용자 계정정보는 Java 파일에 한다.
com.example.demo.config.MySecurityConfiguration

```
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("gildong").password("1234").roles("USER");
		auth.inMemoryAuthentication().withUser("dba").password("1234").roles("DBA","USER");
		auth.inMemoryAuthentication().withUser("admin").password("1234").roles("ADMIN","DBA","USER");
	}
```

3. 로그인 화면은 개발자가 제공하는 것을 사용한다.

```
			.and().formLogin() 
			.loginPage("/login") // 커스텀 로그인 페이지 활성화 > 컨트롤러 추가 > JSP 추가
			.defaultSuccessUrl("/welcome")
			.failureUrl("/login?error")
			.usernameParameter("username")
			.passwordParameter("password")
```
