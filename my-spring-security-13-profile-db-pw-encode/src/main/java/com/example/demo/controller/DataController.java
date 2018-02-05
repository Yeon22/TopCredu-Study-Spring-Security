package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DataController {

	@RequestMapping({ "/", "/home" })
	public String home(Model model, HttpServletRequest request) {
		System.out.println("request.getRequestURI() = "+request.getRequestURI());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth.getName() = "+auth.getName());
		System.out.println("auth.getAuthorities() = "+auth.getAuthorities());
		System.out.println("auth.getPrincipal() = "+auth.getPrincipal());

		model.addAttribute("title", "home.jsp");
		
		return "home";
	}

	/*
	 * remember-me 설정을 적용하면 user 권한으로 manager 컨텐츠에 접근하려고 시도할 때,
	 * 비록 remember-me 처리로 자동인증이 되었다고 하더라도
	 * access denied 가 처리되기 전에 로그인 폼이 먼저 연동된다.
	 * 이 때, manager 컨텐츠에 접근할 수 있는 계정으로 인증받지 못하면 
	 * 그 때서야 access denied 로 처리된다.
	 */
//	@RequestMapping({ "/user", "/manager", "/admin" })
	@RequestMapping({ "/user", "/manager" })
	@ResponseBody
	public String user(HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// 로그인 했다면
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println("로그인 ID : " + userDetail.getUsername());
		}
		
		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();

		if (queryString == null) {
			return requestURL.toString();
		} else {
			return requestURL.append('?').append(queryString).toString();
		}
	}
	
	private boolean isRememberMeAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
	}

	private void setTargetUrlToSessionAfterRetest(HttpServletRequest request, String targetUrl) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("targetUrl", targetUrl);
		}
	}

	/*
	 * manager 인증으로 admin 컨텐츠 접근 시 자동적으로 로그인 페이지로 연동된다.
	 * 하지만, admin 인증으로 admin 컨텐츠 접근 시 리멤버미 처리만으로 바로 컨텐츠로 연동된다.
	 * admin 컨텐츠는 보안을 강화하기 위해서 리멤버미 설정으로 로그인이 과정으로 통과하였다고 하더라도
	 * admin 컨텐츠 접근 시 다시 아이디/패스워드를  물어보는 과정을 거치도록 구성한다.
	 */
	@RequestMapping({ "/admin" })
	public ModelAndView admin(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "admin.jsp");
		
		if (isRememberMeAuthenticated()) { // 리멤버미 쿠키로 로그인 처리가 되었다면
			setTargetUrlToSessionAfterRetest(request, "/admin"); // 접근해야 할 URL을 세션에 기억시키고
			model.addObject("loginRetest", true); // 아이디/패스워드로 재 검증을 한다고 통지 설정을 추가한 다음
			model.setViewName("login"); // 로그인 페이지로 연동시킨다.
		} else {
			model.setViewName("admin");
		}

		return model;
	}
	
}
