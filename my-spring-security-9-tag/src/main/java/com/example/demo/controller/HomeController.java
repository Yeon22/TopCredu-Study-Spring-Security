package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@GetMapping("/home")
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("home");
		model.addObject("title", "WELCOME PAGE");

		return model;
	}

	@GetMapping("/user")
	public ModelAndView user() {
		ModelAndView model = new ModelAndView();
		model.setViewName("user");
		model.addObject("title", "USER PAGE");

		return model;
	}

	@GetMapping("/manager")
	public ModelAndView manager() {
		ModelAndView model = new ModelAndView();
		model.setViewName("manager");
		model.addObject("title", "MANAGER PAGE");

		return model;
	}

	@GetMapping("/admin")
	public ModelAndView admin() {
		ModelAndView model = new ModelAndView();
		model.setViewName("admin");
		model.addObject("title", "ADMIN PAGE");

		return model;
	}

}
