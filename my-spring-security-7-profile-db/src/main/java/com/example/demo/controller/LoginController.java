package com.example.demo.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@GetMapping(params = "error")
	public String loginError(Model model) {
		model.addAttribute("title", "CUSTOM LOGIN FORM");
		model.addAttribute("error", "ERROR: Invalid username and password.");
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
