package com.iem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class EdtApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdtApiApplication.class, args);
	}
}
