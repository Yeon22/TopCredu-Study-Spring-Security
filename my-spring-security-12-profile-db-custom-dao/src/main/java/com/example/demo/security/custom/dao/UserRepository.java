package com.example.demo.security.custom.dao;

import com.example.demo.security.custom.model.User;

public interface UserRepository {
	public User findByUsername(String username);
}
