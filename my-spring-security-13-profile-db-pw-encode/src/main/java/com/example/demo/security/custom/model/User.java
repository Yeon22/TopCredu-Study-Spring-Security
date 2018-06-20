package com.example.demo.security.custom.model;

import java.util.List;

import lombok.Data;

//@Data
public class User {
	private String username;
	private String password;
	private boolean enabled;
	private List<Role> roles;
	
	public User() {
		
	}
	
	public User(String username, String password, boolean enabled, List<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
