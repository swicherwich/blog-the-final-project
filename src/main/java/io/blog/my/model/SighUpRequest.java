package io.blog.my.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class SighUpRequest {
	
	@JsonAlias({"username", "email"})
	private String login;
	
	private String password;
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}