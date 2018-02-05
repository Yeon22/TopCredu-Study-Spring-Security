package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {

	@GetMapping("/")
	public String home() {
		return "redirect:login";
	}

	@GetMapping("/basics")
	public String getBasicContent(Model model) {
		model.addAttribute("title", "LOGIN SUCCESS");
		
		return "basics";
	}
	
	@GetMapping("/secret")
	public String getSecretContent(Model model) {
		model.addAttribute("title", "ADMIN ONLY CONTENT");
		
		return "secret";
	}
}
