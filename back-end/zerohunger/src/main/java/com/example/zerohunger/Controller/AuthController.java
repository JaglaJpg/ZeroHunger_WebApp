package com.example.zerohunger.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		
        Cookie accessTokenCookie = new Cookie("accessToken", tokens.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 60);

        Cookie refreshTokenCookie = new Cookie("refreshToken", tokens.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok("User signed in successfully");
		
		
	}
}
