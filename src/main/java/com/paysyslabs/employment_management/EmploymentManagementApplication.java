package com.paysyslabs.employment_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmploymentManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmploymentManagementApplication.class, args);
	}
	@Bean
	public CommandLineRunner helloWorldRunner() {
		return args -> System.out.println("Hello, World!  ");
	}

}
