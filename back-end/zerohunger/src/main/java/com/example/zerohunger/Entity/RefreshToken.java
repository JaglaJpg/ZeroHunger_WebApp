package com.example.zerohunger.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "RefreshToken")
public class RefreshToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long TokenID;
	
	@OneToOne
	@JoinColumn(name = "userID",referencedColumnName = "userID")
	private Users userID;
	
	@Column(nullable = false, unique = true)
	private String token;
	
	private LocalDateTime expiresAt;
	
	
	public Long getTokenID() {
		return TokenID;
	}
	
	public void setTokenID(Long ID) {
		TokenID = ID;
	}
	
	public Users getUserID() {
		return userID;
	}
	
	public void setUserID(Users ID) {
		userID = ID;
	}
	public LocalDateTime getExpAt() {
		return expiresAt;
	}
	
	public void setExpAt(LocalDateTime exp) {
		expiresAt = exp;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

}
