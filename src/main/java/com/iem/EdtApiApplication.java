package com.iem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import controller.AuthController;
import controller.InitController;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.my.package.first","com.my.package.second"})
public class EdtApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdtApiApplication.class, args);
	}
}
