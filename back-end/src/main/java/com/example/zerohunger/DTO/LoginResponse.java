package com.example.zerohunger.DTO;

import com.example.zerohunger.Entity.Users;

public class LoginResponse {
	private final boolean response;
	private final Users user;
	
	public LoginResponse(boolean response, Users user) {
		this.response = response;
		this.user = user;
	}
	
	public LoginResponse(boolean response) {
		this.response = response;
		this.user = null;
	}
	
	public boolean getResponse() {
		return this.response;
	}
	
	public Users getUser() {
		return this.user;
	}
}
