package web;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginBody implements Serializable {

	private static final long serialVersionUID = -1764970284520387975L;
	
	@JsonProperty(value = "login")
	public String login;
	
	@JsonProperty(value = "password")
	public String password;

}