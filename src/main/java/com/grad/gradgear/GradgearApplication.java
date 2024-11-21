package com.grad.gradgear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class GradgearApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradgearApplication.class, args);
	}

}
