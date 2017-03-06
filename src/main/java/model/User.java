package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


public  class User {

	
	
	@Id @GeneratedValue
	long id;
	
	String login;
	String mail;
	String password;
	
	String group;
	
	
	public User()
	{
		
	}

	
	
	
}

    
    