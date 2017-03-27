package com.iem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import service.AuthService;
import service.LessonService;
import controller.AuthController;
import controller.InitController;
import dao.AuthRepository;
import dao.LessonRepository;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {AuthController.class, InitController.class, LessonService.class, AuthService.class, AuthRepository.class, LessonRepository.class})
public class EdtApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdtApiApplication.class, args);
	}
}
