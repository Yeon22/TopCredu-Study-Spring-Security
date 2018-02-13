Spring Security

1. 시큐리티 설정은 Java 파일에 한다.
com.example.demo.config.MySecurityConfiguration

```
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/dba/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
			.and().formLogin() // 스프링이 제공하는 기본 로그인 페이지 활성화
			.and().exceptionHandling().accessDeniedPage("/denied");
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

3. 로그인 화면은 스프링 시큐리티가 제공하는 것을 사용한다.
