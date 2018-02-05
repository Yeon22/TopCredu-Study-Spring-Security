package com.example.demo.security.custom.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.demo.security.custom.model.Role;
import com.example.demo.security.custom.model.User;

public class MyUser extends org.springframework.security.core.userdetails.User {
	
	private static final long serialVersionUID = -4900594654083865723L;
	private User user;

	public MyUser(User user) {
		super(user.getUsername(), user.getPassword(), authorities(user));
		this.user = user;
	}

	private static Collection<? extends GrantedAuthority> authorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRoles()) {
        	authorities.add(new SimpleGrantedAuthority(role.toString()));
		}
        return authorities;
    }

	public User getUser() {
		return user;
	}

}
