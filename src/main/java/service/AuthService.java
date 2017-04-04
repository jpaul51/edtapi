package service;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CustomUser;
import model.Token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AuthRepository;
import dao.UserRepository;
import web.LoginBody;

@Service
public class AuthService {
	
	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenService tokenService;
	
	public Map<String, String> login(LoginBody loginBody){
		
		if(loginBody == null){
			throw new InvalidParameterException("loginBody should not be null or empty");
		}
		if(loginBody.login == null || loginBody.login.isEmpty()){
			throw new InvalidParameterException("login should not be null or empty");
		}
		if(loginBody.password == null || loginBody.password.isEmpty()){
			throw new InvalidParameterException("password should not be null or empty");
		}
				
		CustomUser user = userRepository.findByLogin(loginBody.login);
		
		if(user == null){
			throw new RuntimeException(String.format("User with login : %s don't exist", loginBody.login));
		}else{
			if(user.getLogin() != null && !user.getLogin().isEmpty() && user.getPassword() != null && !user.getPassword().isEmpty()){
				if(!user.getPassword().equals(loginBody.password)){
					throw new RuntimeException(String.format("User or Password Wrong for login : %s", loginBody.login));
				}
			}
		}
		
		Token userToken = tokenService.findTokenByUser(user);
			
		Map<String, String> res = new HashMap<>();
		res.put("user_id", String.valueOf(user.getId()));
		res.put("access-token", userToken.getAccessToken());
		res.put("refresh_token", userToken.getRefreshToken());
		
		return res;
	}
}