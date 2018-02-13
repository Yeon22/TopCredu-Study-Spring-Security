Spring Security

1. application.properties
프로파일을 적용하면 여러 설정을 미티 해 놓고 상황에 따라서 적용할 수 있다.

```
#spring.profiles.active=xml
spring.profiles.active=javaconfig
```

위 설정에 따라서 활성화 된 프로파일은 javaconfig가 된다.

1.1. 프로파일이 xml 일 때

```
@Profile("xml")
@Configuration
@ImportResource("classpath:spring/security.xml")
public class SecurityConfiguration {

}
```

@Profile("xml") 애노테이션 설정에 따라 프로파일이 xml일 때 SecurityConfiguration 클래스가 사용되고
이에 따라서 security.xml 파일에 설정한 내역이 적용된다.

1.2. 프로파일이 javaconfig 일 때

```
@Profile("javaconfig")
@Configuration
public class SecurityConfigurationJava extends WebSecurityConfigurerAdapter {
	// 생략
}
```

@Profile("javaconfig") 애노테이션 설정에 따라 프로파일이 javaconfig일 때 SecurityConfigurationJava 클래스가 사용되고
이에 따라서 SecurityConfigurationJava 클래스 파일에 설정한 내역이 적용된다.

2. 시큐리티 인증 과정 중 특정한 상황에 로직을 수행하고자 할 때 커스텀 핸들러를 적용할 수 있다.
com.example.demo.config.SecurityConfigurationJava

```
//		http.formLogin().failureUrl("/login?error"); // AuthenticationFailureHandler가 대신 처리한다.
		http.formLogin().failureHandler(new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, 
					HttpServletResponse response, AuthenticationException authenticationException)
					throws IOException, ServletException {
				System.out.println(">>> MyAuthenticationFailureHandler # onAuthenticationFailure() called.");
				System.out.println(">>> " + authenticationException.getMessage());
				
				String redirectUrl = "/login?error";
				System.out.println(">>> redirectUrl = " + redirectUrl);
				response.sendRedirect(redirectUrl);
			}
		});
```

http.formLogin().failureUrl("/login?error") 코드를 설정하는 대신 http.formLogin().failureHandler(코드생략)를 사용한다.
failureHandler 메소드에 전달하는 AuthenticationFailureHandler 구현체는 로직 수행 후 연동할 URL인 "/login?error" 정보를 알려주어야 한다.

```
//		http.formLogin().defaultSuccessUrl("/home"); // AuthenticationSuccessHandler가 대신 처리한다.
		http.formLogin().successHandler(new AuthenticationSuccessHandler() {
			private RequestCache requestCache = new HttpSessionRequestCache();
			private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
			
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				
				System.out.println(">>> Custom AuthenticationSuccessHandler # onAuthenticationSuccess() called.");

				SavedRequest savedRequest = requestCache.getRequest(request, response);
				if (savedRequest != null) {
					String redirectUrl = savedRequest.getRedirectUrl();
					if (redirectUrl != null) {
						System.out.println(">>> redirectUrl = " + redirectUrl);
						redirectStrategy.sendRedirect(request, response, redirectUrl);
						return;
					}
				}
				
				String redirectUrl = request.getContextPath() + "/home";
				System.out.println(">>> redirectUrl = " + redirectUrl);
				response.sendRedirect(redirectUrl);
				
			}
		});
```

http.formLogin().defaultSuccessUrl("/home") 코드를 설정하는 대신 http.formLogin().successHandler(코드생략)를 사용한다.
successHandler 메소드에 전달하는 AuthenticationSuccessHandler 구현체는 로직 수행 후 연동할 URL인 "/home" 정보를 알려주어야 한다.

```
		// XML 설정 security 네임스페이스에서는 설정할 수 있는 속성이 없다.
		http.logout().addLogoutHandler(new LogoutHandler() {
			@Override
			public void logout(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) {
				System.out.println(">>> Custom LogoutHandler # logout() called.");
			}
		});
```

사용자가 로그아웃 할 때 로직을 수행할 필요가 있다면 LogoutHandler 구현체를 http.logout().addLogoutHandler() 메소드에 파라미터로 전달한다.
XMl 빈 설정으로 LogoutHandler를 등록할 수 있다. 
참고 https://stackoverflow.com/questions/3112972/spring-security-customize-logout-handler

```
//		http.logout().logoutSuccessUrl("/login?logout"); // LogoutSuccessHandler가 대신 처리한다.
		http.logout().logoutSuccessHandler(new LogoutSuccessHandler() {
			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				
				System.out.println(">>> Custom LogoutSuccessHandler # onLogoutSuccess() called.");

				if (authentication != null && authentication.getDetails() != null) {
					request.getSession().invalidate();
					/*
					 * TODO: logout logging
					 */
				}

				response.setStatus(HttpServletResponse.SC_OK);
				
				String redirectUrl = "/login?logout";
				System.out.println(">>> redirectUrl = " + redirectUrl);
				response.sendRedirect(redirectUrl);
				
			}
		});
```

http.logout().logoutSuccessUrl("/login?logout") 코드를 설정하는 대신 http.logout().logoutSuccessHandler(코드생략)를 사용한다.
logoutSuccessHandler 메소드에 전달하는 LogoutSuccessHandler 구현체는 로직 수행 후 연동할 URL인 "/login?logout" 정보를 알려주어야 한다.

LogoutHandler VS LogoutSuccessHandler
일반적으로 LogoutHandler 구현은 로그 아웃 처리에 참여할 수있는 클래스를 나타냅니다. 필요한 정리를 수행하기 위해 호출 될 것으로 예상됩니다. 따라서 예외를 던져서는 안됩니다.
LogoutSuccessHandler는 LogoutFilter에 의한 성공적인 로그 아웃 이후에 호출됩니다. 리디렉션 또는 적절한 대상으로 전달할 수 있습니다. 인터페이스는 LogoutHandler와 거의 같지만 예외가 발생할 수 있습니다.

```
//		http.exceptionHandling().accessDeniedPage("/login/denied"); // AccessDeniedHandler가 대신 처리한다.
		http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException exception) throws IOException, ServletException {
				
				System.out.println(">>> Custom AccessDeniedHandler # handle() called.");

				String redirectUrl = "/login/denied";
				System.out.println(">>> redirectUrl = " + redirectUrl);
				response.sendRedirect(redirectUrl);
				
			}
		});
```

http.exceptionHandling().accessDeniedPage("/login/denied") 코드를 설정하는 대신 http.exceptionHandling().accessDeniedHandler(코드생략)를 사용한다.
accessDeniedHandler 메소드에 전달하는 AccessDeniedHandler 구현체는 로직 수행 후 연동할 URL인 "/login/denied" 정보를 알려주어야 한다.

View 접근 시 스프링 시큐리티 필터들이 작동하지 않도록 하기

```
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 시큐리티 적용대상에서 제외한다.
		web.ignoring().antMatchers("/WEB-INF/**");
	}
```

스프링 시큐리티는 서블릿 필터를 사용한다. 따라서, 포워드 되지 않고 리다이렉트 되는 경우 시큐리티 필터들이 또 적용되는 데 그럴 필요가 있을까?
