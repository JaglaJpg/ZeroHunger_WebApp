package com.example.zerohunger.Utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
	private static final BCryptPasswordEncoder hash = new BCryptPasswordEncoder();
	
	public static String hashPassword(String password) {
		return hash.encode(password);
	}
	
	public static boolean verifyPassword(String plainPassword, String hashedPassword) {
		return hash.matches(plainPassword, hashedPassword);
	}
}
