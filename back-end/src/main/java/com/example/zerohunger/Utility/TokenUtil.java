package com.example.zerohunger.Utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
	private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String generateAccessToken(Long userID) {
		return Jwts.builder()
		        .setSubject(userID.toString())           
		        .setIssuedAt(new Date())    
		        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) 
		        .signWith(key) 
		        .compact();
	}
	
	public String generateRefreshToken(Long userID) {
	    return Jwts.builder()
	            .setSubject(userID.toString())
	            .setIssuedAt(new Date())      
	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) 
	            .signWith(key) 
	            .compact();
	}

}
