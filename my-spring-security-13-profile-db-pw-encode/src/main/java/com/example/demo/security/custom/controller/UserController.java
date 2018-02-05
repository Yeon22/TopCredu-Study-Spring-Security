package com.example.demo.security.custom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.custom.model.User;
import com.example.demo.security.custom.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/user/{username}")
	public User getUser(@PathVariable String username) {
		return userService.findByUsername(username);
	}

}
