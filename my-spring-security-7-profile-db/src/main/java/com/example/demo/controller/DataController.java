package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataController {

	@RequestMapping({ "/", "/home" })
	public String home(Model model) {
		model.addAttribute("title", "home.jsp");
		return "home";
	}

	@RequestMapping({ "/user", "/manager", "/admin" })
	@ResponseBody
	public String user(HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// 로그인 했다면
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			// UserDetails: 스프링이 유저 정보를 취급하기 위해서 선언한 모델클래스 
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

}
