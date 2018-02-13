package com.example.demo.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

@Profile("javaconfig")
@Configuration
public class SecurityConfigurationJava extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
		auth.inMemoryAuthentication().withUser("manager").password("manager").roles("MANAGER", "USER");
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN", "MANAGER", "USER");
	}

	private String getUrl(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		StringBuffer requestURL = req.getRequestURL();
		String queryString = req.getQueryString();

		if (queryString == null) {
			return requestURL.toString();
		} else {
			return requestURL.append('?').append(queryString).toString();
		}
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * 시큐리티의 특정 필터 전에 커스텀 필터를 기동시킬 수 있다.
		 * UsernamePasswordAuthenticationFilter 전에 커스텀 필터를 적용한다.
		 */
		http.addFilterBefore(new Filter() {
			@Override
			public void init(FilterConfig config) throws ServletException {
				
			}

			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
					throws IOException, ServletException {
				System.out.println("==> Custom Filter ==> ");
				System.out.println(getUrl(request));
				
				chain.doFilter(request, response);
				
				System.out.println("<== Custom Filter <==");
			}

			@Override
			public void destroy() {
				
			}
		}, UsernamePasswordAuthenticationFilter.class);

		// hasRole 메소드를 사용하면 접두사 "ROLE_" 생략이 가능하다.
		http.authorizeRequests()
			.antMatchers("/user/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/admin/**").hasRole("ADMIN")
			// 자바 정규표현식을 사용하여 설정할 수 있다. 인증된 사용자만이 comment URL로 POST 요청을 할 수 있다.
			.regexMatchers(HttpMethod.POST, "/comment/.*").authenticated()
			//.anyRequest().authenticated(); // 모든 접근은 인증받아야 한다.
			.anyRequest().permitAll(); // 명시하지 않은 모든 접근은 허용한다.
		
		http.formLogin().loginPage("/login"); // 커스텀 로그인페이지를 사용한다. 로그인 페이지 접근 시 사용할 URL
		http.formLogin().loginProcessingUrl("/login"); // 로그인 폼의 action에 설정할 URL
		http.formLogin().usernameParameter("username");
		http.formLogin().passwordParameter("password");
		
//		http.formLogin().failureUrl("/login?error"); // AuthenticationFailureHandler가 대신 처리한다.
		http.formLogin().failureHandler(new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
					throws IOException, ServletException {
				System.out.println(">>> MyAuthenticationFailureHandler # onAuthenticationFailure() called.");
				System.out.println(">>> " + authenticationException.getMessage());
				
				String redirectUrl = "/login?error";
				System.out.println(">>> redirectUrl = " + redirectUrl);
				response.sendRedirect(redirectUrl);
			}
		});

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

		// XML 설정 security 네임스페이스에서는 설정할 수 있는 속성이 없다.
		http.logout().addLogoutHandler(new LogoutHandler() {
			@Override
			public void logout(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) {
				System.out.println(">>> Custom LogoutHandler # logout() called.");
			}
		});

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

		http.logout().logoutUrl("/logout"); // 로그아웃 처리 폼의 action 속성에 할당할 연동 주소
		http.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID", "SPRING_SECURITY_REMEMBER_ME_COOKIE");
		
		http.csrf();

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
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 시큐리티 적용대상에서 제외한다.
		web.ignoring().antMatchers("/WEB-INF/**");
	}

}
