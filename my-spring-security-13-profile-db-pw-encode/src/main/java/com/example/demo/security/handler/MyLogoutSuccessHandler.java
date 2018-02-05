package com.example.demo.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		System.out.println(">>> MyLogoutSuccessHandler # onLogoutSuccess() called.");

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

}
