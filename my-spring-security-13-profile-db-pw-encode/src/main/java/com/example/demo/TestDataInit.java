package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.demo.security.custom.dao.UserRepository;
import com.example.demo.security.custom.model.Role;
import com.example.demo.security.custom.model.User;

@Profile("prod") // 테스트 데이터를 처리하므로 어감은 dev가 맞겠지만 연습 프로젝트이고 관련설정을 바꾸어야 하는 번거로움이 있으므로 그냥 두겠다.
@Component
public class TestDataInit {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void testDataInsert() {
		User user = new User();
		user.setUsername("user");
		user.setPassword("user");
		user.setEnabled(true);
		List<Role> userRoles = new ArrayList<>();
		userRoles.add(Role.ROLE_USER);
		user.setRoles(userRoles);
		userRepository.insert(user);

		User manager = new User();
		manager.setUsername("manager");
		manager.setPassword("manager");
		manager.setEnabled(true);
		List<Role> managerRoles = new ArrayList<>();
		managerRoles.add(Role.ROLE_MANAGER);
		managerRoles.add(Role.ROLE_USER);
		manager.setRoles(userRoles);
		userRepository.insert(manager);

		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setEnabled(true);
		List<Role> adminRoles = new ArrayList<>();
		adminRoles.add(Role.ROLE_ADMIN);
		adminRoles.add(Role.ROLE_MANAGER);
		adminRoles.add(Role.ROLE_USER);
		admin.setRoles(adminRoles);
		userRepository.insert(admin);
	}
	
}
