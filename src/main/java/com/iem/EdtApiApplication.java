package com.iem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import controller.InitController;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackageClasses=InitController.class)
public class EdtApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdtApiApplication.class, args);
	}
}
