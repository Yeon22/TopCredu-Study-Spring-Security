package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("dev")
@Configuration
@ImportResource({ "classpath:spring/security.xml" })
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/*
	 * 이 메소드에 설정이 적용되지 않는다. spring/security.xml 파일에 설정이 적용된다.
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated();

		/*
		 * 정적인 h2 로그인 화면에 CSRF 토큰을 추가할 수 없다.
		 * <csrf disabled="true"/>
		 */
		
		httpSecurity.csrf().disable();
		
//		h2-console 에 대해서만 CSRF 면제 처리를 해준다.
//		httpSecurity.csrf().ignoringAntMatchers("/h2-console/**"); 
		
		/*
		 * h2 로그인 화면은 프레임을 사용한다. X-Frame-Options 에 면제 처리를 해준다.
		 * <headers>
		 * 	<frame-options disabled="true"/>
		 * </headers>
		 */
		
		httpSecurity.headers().frameOptions().disable();
	}
}
