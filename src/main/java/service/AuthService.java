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
		res.put("access_token", userToken.getAccessToken());
		res.put("refresh_token", userToken.getRefreshToken());
		
		return res;
	}
	
	public Map<String, String> getToken(String refreshToken, Long userID){
		
		if(refreshToken == null || refreshToken.isEmpty()){
			throw new InvalidParameterException("refreshToken should not be null or empty");
		}
		if(userID == null || userID.intValue() == 0){
			throw new InvalidParameterException("userID should not be null or empty");
		}
		
		CustomUser user = userRepository.findOne(userID);
		Token token = tokenService.refreshToken(user, refreshToken);
		
		Map<String, String> res = new HashMap<>();
		res.put("user_id", String.valueOf(user.getId()));
		res.put("access_token", token.getAccessToken());
		res.put("refresh_token", token.getRefreshToken());
		
		return res;
	}
	
	public Map<String, String> detailsUser(String accessToken, Long userID, Long selectedUserId){
		
		if(accessToken == null || accessToken.isEmpty()){
			throw new InvalidParameterException("refreshToken should not be null or empty");
		}
		if(userID == null || userID.intValue() == 0){
			throw new InvalidParameterException("userID should not be null or empty");
		}
		if(selectedUserId == null || selectedUserId.intValue() == 0){
			throw new InvalidParameterException("selectedUserId should not be null or empty");
		}
		
		CustomUser currentUser = userRepository.findOne(userID);
		if(currentUser == null){
			throw new RuntimeException(String.format("User with id : %s don't exist", userID.toString()));
		}
		tokenService.validateAccessToken(currentUser, accessToken);
		
		/* VERIF DROIT VOIR USER EN FNC DU GROUPE
		if(currentUser != null){
			if(!currentUser.getGroup().equals("Admin")){
				throw new 403 //TODO
			}
		}
		*/
		
		CustomUser selectedUser = userRepository.findOne(selectedUserId);
		
		if(selectedUser == null){
			throw new RuntimeException(String.format("User with id : %s don't exist", selectedUserId.toString()));
		}
		
		Map<String, String> res = new HashMap<>();
			res.put("user_login", selectedUser.getLogin());
		res.put("user_password", selectedUser.getPassword());
		res.put("user_mail", selectedUser.getMail());
		res.put("user_group", selectedUser.getGroup());
		return res;
	}
}