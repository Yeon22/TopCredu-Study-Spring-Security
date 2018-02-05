package com.example.demo.security.custom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.security.custom.model.User;
import com.example.demo.security.custom.service.UserService;

/*
 * More than one UserDetailsService registered. 
 * Please use a specific Id reference in <remember-me/> <openid-login/> or <x509 /> elements.
 */
@Profile("prod")
@Service
public class MyUserService implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		return new MyUser(user);
	}

}
