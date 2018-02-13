Spring Security

1. 시큐리티 설정은 XML 파일에 한다.
resources/spring/security.xml

2. 사용자 계정정보는 XML 파일에 한다.
resources/spring/security/users.xml

3. 로그인 화면을 개발자가 만든 화면을 사용하도록 설정한다.
스프링 시큐리티 필터가 보안과 관련한 인터셉팅을 하게 되고 login-page="/login" 설정에 따라 해당 URL로 리다이렉트 된다.
이에 따라 URL 핸들러 LoginController 클래스를 만든다.

```
		<form-login login-page="/login" 
			default-target-url="/basics"
			authentication-failure-url="/login?error" 
			username-parameter="username"
			password-parameter="password" />
```

default-target-url="/basics"
로그인 인증이 정상적으로 이루어진 다음 사용자가 리다이렉트되어 접근할 URL을 설정한다.

authentication-failure-url="/login?error"
로그인 인증과정에서 에러가 발생하면 사용자가 리다이렉트되어 접근할 URL을 설정한다.

username-parameter="username"/password-parameter="password"
로그인 화면에서 입력 엘리먼트가 설정하게 될 name 값을 지정한다.