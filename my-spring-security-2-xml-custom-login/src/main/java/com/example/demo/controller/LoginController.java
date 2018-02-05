package com.example.demo.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		model.addObject("title", "CUSTOM LOGIN EXAMPLE");
		
		if (error != null) {
			model.addObject("error", "ERROR: Invalid username and password.");
		}
		if (logout != null) {
			model.addObject("msg", "INFO: You have been logged out successfully.");
		}

		return model;
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
