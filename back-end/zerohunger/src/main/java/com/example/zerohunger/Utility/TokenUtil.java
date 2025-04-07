package com.example.zerohunger.Utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    private final SecretKey key;

    @Autowired
    public TokenUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
	
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
	
	public boolean validateToken(String token) {
	    try {
	        Jwts.parserBuilder()
	            .setSigningKey(key)
	            .build()
	            .parseClaimsJws(token); 
	        return true;
	    } catch (Exception e) {
	        return false; 
	    }
	}
	
	public Long extractUserId(String token) {
	    return Long.parseLong(Jwts.parserBuilder()
	        .setSigningKey(key) 
	        .build()
	        .parseClaimsJws(token)
	        .getBody()
	        .getSubject()); 
	}



}
