package service;

import java.util.Random;

import model.CustomUser;
import model.Token;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.TokenRepository;

@Service
public class TokenService {
	
	@Autowired
	private TokenRepository tokenRepository;
	
	Token createToken(CustomUser user){
		Token token = new Token();
		token.setUser(user);
		token.setAccessToken(randomString());
		token.setRefreshToken(randomString());
		token.setCreationDate(new DateTime().plusHours(1)); //Access token available for 1H
		
		tokenRepository.save(token);
		
		return token;
	}
	
	Token refreshToken(CustomUser user, String refreshToken){
		Token token = findTokenByUser(user);
		
		if(token == null){
			token = createToken(user);
		} else {
			if (token.getRefreshToken().equals(refreshToken)){
				token = createToken(user);
			} else {
				throw new RuntimeException(String.format("wrong refresh token for user with login : %s", user.getLogin()));
			}
		}
		
		return token;
	}
	
	Token findTokenByUser(CustomUser user){
		Token token = tokenRepository.findTokenByUser(user);
		
		if(token == null){
			token = createToken(user);
		}
		
		if(token.getCreationDate().isBeforeNow()){
			throw new RuntimeException("Token Expired !");
		}
		
		return token;
	}
	
	
	
	private String randomString(){
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWWYZ0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		
		return String.valueOf(sb);
	}

}
