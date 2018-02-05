package com.example.demo.security.custom.service;

import com.example.demo.security.custom.model.User;

public interface UserService {
	public User findByUsername(String username);
}
