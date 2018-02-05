package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
/*
 * <security:global-method-security pre-post-annotations="enabled" secured-annotations="enabled" jsr250-annotations="enabled" />
 * pre-post-annotations="enabled" : @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter
 * secured-annotations="enabled" : @Secured
 * jsr250-annotations="enabled" : @RolesAllowed
 */
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true, jsr250Enabled=true)
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
		auth.inMemoryAuthentication().withUser("manager").password("manager").roles("MANAGER","USER");
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN","MANAGER","USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/manager**").access("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
			.antMatchers("/user**").access("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
			.and().formLogin() 
			.loginPage("/login") // 커스텀 로그인 페이지 활성화 > 컨트롤러 추가 > JSP 추가
			.defaultSuccessUrl("/home")
			.failureUrl("/login?error")
			.usernameParameter("username")
			.passwordParameter("password")
			.and().logout().logoutSuccessUrl("/login?logout")
			.and().exceptionHandling().accessDeniedPage("/login/denied");
	}

}
