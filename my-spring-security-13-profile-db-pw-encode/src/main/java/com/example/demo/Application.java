package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.security.custom.dao.UserRepository;
import com.example.demo.security.custom.model.Role;
import com.example.demo.security.custom.model.User;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private UserRepository userRepository;
	
	/*
	 * PasswordEncoder 빈 설정을 SecurityConfigurationJava 클래스에 두면
	 * Application 클래스에서 제대로 @Autowired를 받지 못한다.
	 * 암호화 테스트를 위해서 코드적으로 처리해서 테이블에 입력할 필요가 있어서 자리를 이쪽으로 옮겨 놓았다.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("admin");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setEnabled(true);
		
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_MANAGER);
		roles.add(Role.ROLE_USER);
		
		user.setRoles(roles);
		
		userRepository.insert(user);
	}
}
