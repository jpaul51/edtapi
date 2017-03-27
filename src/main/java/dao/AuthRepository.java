package dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository {
	
	public Map<String, String> login(String login, String password){
		
		Map<String, String> result = new HashMap<>();
		result.put("userID", "1");
		result.put("accessToken", "zEFHFOQHeskfeifsifuZKQDKBZKkzdkz");
		result.put("refreshToken", "lhvisguzHFjfjhGViluyOMoklJkgjhga");

		return result;
	}

}