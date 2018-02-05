package com.example.demo.security.handler;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@PostConstruct
	public void init() {
		// 리멤버미로 인증되었지만 리멤버미 쿠키 도난을 대비하여 중요한 컨텐츠는 아이디/패스워드를 다시 물어보고자 할 때
		// 재 인증 성공 후 연동해야할 URL을 처리하는 용도로 사용할 문자열을 받기위한 키값을 설정한다.
		// 인증 성공 후 연동할 URL을 로그인 폼으로부터 받을 때 사용한다.
		// 설정 예 : action="/login?targetUrl=${targetUrl}"
		setTargetUrlParameter("targetUrl");
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println(">>> MyAuthenticationSuccessHandler # onAuthenticationSuccess() called.");

		// 유효한 targetUrl 설정정보가 존재한다면 
		// AbstractAuthenticationTargetUrlRequestHandler 부모 클래스가 제공하는 메소드를 사용하여 처리한다.
		String targetUrl = determineTargetUrl(request, response);
		if (targetUrl != null && !targetUrl.equals("") && !targetUrl.equals("/")) {
			System.out.println(">>> targetUrl = " + targetUrl);
			response.sendRedirect(targetUrl);
			return;
		}
		
		// 시큐리티 필터가 인터셉트한 경우 성공 후 연동 할 URL을 처리한다.
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String redirectUrl = savedRequest.getRedirectUrl();
			if (redirectUrl != null) {
				System.out.println(">>> redirectUrl = " + redirectUrl);
				redirectStrategy.sendRedirect(request, response, redirectUrl);
				return;
			}
		}
		
		// 로그인 링크를 통해 로그인 폼 화면에 접근했고, 로그인 성공 후 연동할 정적인 URL을 설정한다.
		String redirectUrl = request.getContextPath() + "/home";
		System.out.println(">>> redirectUrl = " + redirectUrl);
		response.sendRedirect(redirectUrl);
	}
}
