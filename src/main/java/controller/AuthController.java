package controller;

import java.util.Map;

import javax.transaction.Transactional;

import model.Token;
import model.CustomUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import dao.AuthRepository;
import dao.UserRepository;
import service.AuthService;
import web.LoginBody;
import web.LoginResponse;

@RestController
@Configuration
@EnableJpaRepositories("dao")
@EntityScan("model")
@Transactional
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private AuthRepository 	auth;

	@RequestMapping(value = "/proxima/users/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> login(@RequestBody LoginBody body)
	{
		return authService.login(body);
	}
	
	@RequestMapping(value = "proxima/debug/addUser", method = RequestMethod.GET)
	@ResponseBody
	public String login()
	{
		CustomUser user = new CustomUser();
		user.setLogin("proximaTest");
		user.setMail("proxima@example.com");
		user.setPassword("amixorp/2017");
		
		repo.save(user);
		
		return "USER CREATED";
	}
	
}