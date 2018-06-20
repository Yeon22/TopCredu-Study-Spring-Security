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

	// 시큐리티가 사용하는 메소드
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 우리의 서비스 메소드가 우리가 만든 모델 클래스를 리턴한다.
		User user = userService.findByUsername(username);
		
		// 우리가 만든 모델 클래스를 시큐리티가 이해하는 모델클래스로 변경하여 리턴한다.
		return new MyUser(user);
	}

}
