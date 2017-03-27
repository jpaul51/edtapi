package service;

import java.security.InvalidParameterException;
import java.util.Map;

import model.Token;
import model.User;

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
		
		User user = new User();
		
		userRepository.findOne(Long.valueOf(1));
		
		Token token = authRepository.findByUser(user);
		System.out.println(token.getAccessToken());
		
		return null;
	}
}