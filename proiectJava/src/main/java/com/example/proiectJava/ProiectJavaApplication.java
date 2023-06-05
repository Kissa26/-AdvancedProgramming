package com.example.proiectJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class ProiectJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProiectJavaApplication.class, args);
	}

}
