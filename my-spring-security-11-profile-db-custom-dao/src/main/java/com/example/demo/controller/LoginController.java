package com.example.demo.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	public String loginForm(Model model) {
		model.addAttribute("title", "CUSTOM LOGIN FORM");
		return "login";
	}

	private String getRememberMeTargetUrlFromSession(HttpServletRequest request) {
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if (session != null) {
			targetUrl = session.getAttribute("targetUrl") == null ? "" : session.getAttribute("targetUrl").toString();
		}
		return targetUrl;
	}
	
	@GetMapping(params = "error")
	public String loginError(Model model, HttpServletRequest request) {
		model.addAttribute("title", "CUSTOM LOGIN FORM");
		model.addAttribute("error", "ERROR: Invalid username and password.");
		
		// 재 인증과정에서 에러가 발생하는 경우, 세션으로부터 targetUrl을 찾아서 로그인 페이지에 건네준다.
		String targetUrl = getRememberMeTargetUrlFromSession(request);
		if (StringUtils.hasText(targetUrl)) {
			model.addAttribute("targetUrl", targetUrl);
			model.addAttribute("loginRetest", true);
		}
		
		return "login";
	}

	@GetMapping(params = "logout")
	public String logoutInfo(Model model) {
		model.addAttribute("title", "CUSTOM LOGIN FORM");
		model.addAttribute("msg", "INFO: You have been logged out successfully.");
		return "login";
	}

	@GetMapping("/denied")
	public ModelAndView accesssDenied(Principal user) {
		ModelAndView model = new ModelAndView();
		model.setViewName("handle403");
		model.addObject("title", "SECURITY INFORMATION");

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName() + ", you do not have permission to access this page.");
		} else {
			model.addObject("msg", "You do not have permission to access this page.");
		}

		return model;
	}

}
