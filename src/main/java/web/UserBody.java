package web;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserBody implements Serializable {

	private static final long serialVersionUID = -1764970284520387975L;
	
	@JsonProperty(value = "user_login")
	public String login;
	
	@JsonProperty(value = "user_password")
	public String password;
	
	@JsonProperty(value = "user_mail")
	public String mail;
	
	@JsonProperty(value = "user_group")
	public String group;
}