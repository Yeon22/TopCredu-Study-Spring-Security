package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	// PMD
	private String noUse;

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void print() {
		String a = null;
		
		// C:/Lesson4/spring-security/my-spring-test/src/main/java/com/example/demo/MySpringTestApplication.java:17 
		// Null pointer dereference of a in 
		// com.example.demo.MySpringTestApplication.print() [Scary(5), High confidence]
		// >> 변수 a 가 Null인데 equals를 사용하면 Null 인데 사용했다고 Null pointer 오류가 발생한다.
		if (a.equals("a")) {
			a = "error";
		}
		
		// 아래 코드는 FindBugs를 사용할 때 버그가 없다고 판단된다.
		if ("a".equals(a)) {
			a = "error";
		}

	}
}
