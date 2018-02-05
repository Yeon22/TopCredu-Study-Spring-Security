package com.example.demo.security.custom.model;

import java.util.List;

import lombok.Data;

@Data
public class User {
	private String username;
	private String password;
	private boolean enabled;
	private List<Role> roles;
}
