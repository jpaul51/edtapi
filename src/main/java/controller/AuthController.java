package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import service.AuthService;
import web.LoginBody;
import web.LoginResponse;

@RestController
@Configuration
@EnableJpaRepositories("dao")
@EntityScan("model")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/proxima/users/login", method = RequestMethod.POST)
	@ResponseBody
	public LoginResponse login(@RequestBody LoginBody body)
	{
		return authService.login(body);
	}
	
	@RequestMapping(value = "/proxima/user/login", method = RequestMethod.GET)
	@ResponseBody
	public String login()
	{
		return "zob";
	}
	
}