Spring Security

1. 보안을 강화하기 위해서 패스워드 정보는 암호화 인코딩을 거친 후 저장한다.
사용자 인증 시 패스워드를 복호화 하여 비교하지 않는다. 로그인 폼으로 받은 패스워드를 암호화 한 후 디비에 저장된 정보와 비교하여 검증한다.

com.example.demo.config.SecurityConfigurationJava

```
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		auth.authenticationProvider(authenticationProvider);
	}
```

auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()) 코드로 적용할 수 있다.
또는, auth.authenticationProvider(authenticationProvider) 코드로 적용할 수도 있다.

AuthenticationProvider 인터페이스를 구현한 MyAuthenticationProvider 객체를 사용하면 개발자가 직접 인증과 관련한 작업을 제어할 수 있다.
특별한 이유가 없다면 굳이 사용할 필요는 없다고 보인다.

스프링 시큐리티의 암호화 모듈 3가지
BCryptPasswordEncoder, NoOpPasswordEncoder, StandardPasswordEncoder


2. 테스트 유저정보를 암호화 한 후 디비에 입력하기

com.example.demo.TestDataInit
