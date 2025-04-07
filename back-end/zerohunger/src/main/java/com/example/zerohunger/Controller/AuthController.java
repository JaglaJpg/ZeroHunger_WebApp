package com.example.zerohunger.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.zerohunger.Service.LocationService;
import com.example.zerohunger.Service.SessionService;
import com.example.zerohunger.Service.UsersService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import com.example.zerohunger.DTO.LoginRequest;
import com.example.zerohunger.DTO.LoginResponse;
import com.example.zerohunger.DTO.SessionTokens;
import com.example.zerohunger.Entity.Users;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UsersService usersService;
	private final SessionService sessionService;
	private final LocationService locationService;
	
	public AuthController(UsersService usersService, SessionService sessionService, 
			LocationService locationService) {
		this.usersService = usersService;
		this.sessionService = sessionService;
		this.locationService = locationService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody Users newUser){
		if (!locationService.isValidAddress(newUser.getAddress())) {
	        return ResponseEntity.badRequest().body("Invalid address provided. Please enter a valid address.");
	    } else if(!usersService.verifyAge(newUser.getDOB())) {
	    	return ResponseEntity.unprocessableEntity().body("User Not Old Enough");
	    }
		usersService.addUser(newUser);
		
		return ResponseEntity.ok(newUser);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody LoginRequest request, HttpServletResponse response){
		LoginResponse loginResponse = usersService.VerifyUser(request);
		if(!loginResponse.getResponse()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");

		}
		
		SessionTokens tokens = sessionService.CreateSession(loginResponse.getUser());
		
		response.setHeader("Set-Cookie", "accessToken=" + tokens.getAccessToken()
	    + "; HttpOnly; Path=/; Max-Age=3600; SameSite=None; Secure");

	response.addHeader("Set-Cookie", "refreshToken=" + tokens.getRefreshToken()
	    + "; HttpOnly; Path=/; Max-Age=604800; SameSite=None; Secure");

        return ResponseEntity.ok("User signed in successfully");
		
		
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logOut(HttpServletResponse response){
		 Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//long userId = 1111;
		 sessionService.CleanUpSession(userId);
		 
		 
		 response.setHeader("Set-Cookie", "accessToken=; HttpOnly; Path=/; Max-Age=0; SameSite=None; Secure");
		 response.addHeader("Set-Cookie", "refreshToken=; HttpOnly; Path=/; Max-Age=0; SameSite=None; Secure");

		 
		 return ResponseEntity.ok(null);
		 
	}
}
