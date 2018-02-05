package com.example.demo.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
			throws IOException, ServletException {
		System.out.println(">>> MyAuthenticationFailureHandler # onAuthenticationFailure() called.");
		System.out.println(">>> " + authenticationException.getMessage());
		
		String redirectUrl = "/login?error";
		System.out.println(">>> redirectUrl = " + redirectUrl);
		response.sendRedirect(redirectUrl);
	}

}
