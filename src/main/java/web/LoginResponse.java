package web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
	
	@JsonProperty(value = "user_id")
	public String userID;
	
	@JsonProperty(value = "user_id")
	public String accessToken;
	
	@JsonProperty(value = "refresh_token")
	public String refreshToken;
}