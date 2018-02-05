package com.example.demo.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "redirect:welcome";
	}
	
	@GetMapping("/welcome")
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("welcome");
		model.addObject("title", "WELCOME PAGE");

		return model;
	}

	@GetMapping("/admin")
	public ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("admin");
		model.addObject("title", "ADMIN PAGE");

		return model;
	}

	@GetMapping("/dba")
	public ModelAndView dbaPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("admin");
		model.addObject("title", "DBA PAGE");

		return model;
	}

	@GetMapping("/denied")
	public ModelAndView accesssDenied(Principal user) {
		ModelAndView model = new ModelAndView();
		model.setViewName("handle403");
		model.addObject("title", "ACCESS DENIED INFORMATION PAGE");
		
		if (user != null) {
			model.addObject("msg", "Hi " + user.getName() + ", you do not have permission to access this page!");
		} else {
			model.addObject("msg", "You do not have permission to access this page!");
		}

		return model;
	}

}
