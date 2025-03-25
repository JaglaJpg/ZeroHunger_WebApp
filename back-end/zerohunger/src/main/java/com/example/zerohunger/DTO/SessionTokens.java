package com.example.zerohunger.DTO;

public class SessionTokens {
	private final String accessToken;
	private final String refreshToken;
	
	public SessionTokens(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public String getRefreshToken() {
		return this.refreshToken;
	}

}
