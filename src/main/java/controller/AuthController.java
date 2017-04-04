package controller;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import dao.UserRepository;
import service.AuthService;
import web.LoginBody;
import web.UserBody;

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

	@RequestMapping(value = "/proxima/users/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> login(@RequestBody LoginBody body)
	{
		return authService.login(body);
	}
	
	@RequestMapping(value = "/proxima/token", method = RequestMethod.GET)
	public Map<String, String> getToken(
			@RequestHeader(value = "refresh_token") String refreshToken,
			@RequestHeader(value = "user_id") Long userId
			){
		return authService.getToken(refreshToken, userId);
	}
	
	@RequestMapping(value = "/proxima/users/{user_id}", method = RequestMethod.GET)
	public Map<String, String> detailsUser(
			@RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "user_id") Long userId,
			@PathVariable("user_id") Long selectedUserId){
		
		return authService.detailsUser(accessToken, userId, selectedUserId);
	}
	
	@RequestMapping(value = "proxima/users", method = RequestMethod.POST)
	@ResponseBody
	public void addUser(
			@RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "user_id") Long userId,
			@RequestBody UserBody body)
	{
		authService.addUser(accessToken, userId, body);
	}
	
	@RequestMapping(value = "proxima/users/{user_id}", method = RequestMethod.PUT)
	@ResponseBody
	public void editUser(
			@RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "user_id") Long userId,
			@RequestBody UserBody body,
			@PathVariable("user_id") Long selectedUserId)
	{
		authService.editUser(accessToken, userId, body, selectedUserId);
	}
}