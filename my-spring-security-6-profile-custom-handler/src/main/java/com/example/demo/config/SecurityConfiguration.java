package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Profile("xml")
@Configuration
@ImportResource("classpath:spring/security.xml")
public class SecurityConfiguration {

}
