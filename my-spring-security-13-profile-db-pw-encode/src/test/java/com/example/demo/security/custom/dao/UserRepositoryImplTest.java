package com.example.demo.security.custom.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.security.custom.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryImplTest {
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void test() {
		User user = userRepository.findByUsername("admin");
		System.out.println(user);
	}

}
