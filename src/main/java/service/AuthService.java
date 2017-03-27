package service;

import java.security.InvalidParameterException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AuthRepository;
import web.LoginBody;
import web.LoginResponse;

@Service
public class AuthService {
	
	@Autowired
	private AuthRepository authRepository;

	public LoginResponse login(LoginBody loginBody){
		
		if(loginBody == null){
			throw new InvalidParameterException("loginBody should not be null or empty");
		}
		if(loginBody.login == null || loginBody.login.isEmpty()){
			throw new InvalidParameterException("login should not be null or empty");
		}
		if(loginBody.password == null || loginBody.password.isEmpty()){
			throw new InvalidParameterException("password should not be null or empty");
		}
		
		Map<String, String> bulkResponse = authRepository.login(loginBody.login, loginBody.password);
		
		LoginResponse response = new LoginResponse();
		
		if(bulkResponse.containsKey("userID")){
			response.userID = bulkResponse.get("userID");
		}
		
		if(bulkResponse.containsKey("accessToken")){
			response.accessToken = bulkResponse.get("accessToken");
		}
		
		if(bulkResponse.containsKey("refreshToken")){
			response.refreshToken = bulkResponse.get("refreshToken");
		}
		
		return response;	
	}
}