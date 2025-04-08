package com.example.zerohunger.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zerohunger.DTO.SessionTokens;
import com.example.zerohunger.Entity.RefreshToken;
import com.example.zerohunger.Entity.Sessions;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Repository.RefreshTokenRepo;
import com.example.zerohunger.Repository.SessionRepo;
import com.example.zerohunger.Utility.TokenUtil;

import jakarta.transaction.Transactional;

@Service
public class SessionService {
	private SessionRepo seshRepo;
	private RefreshTokenRepo refreshRepo;
	private TokenUtil tokenUtil;
	
	@Autowired
	public SessionService(SessionRepo seshRepo, RefreshTokenRepo refreshRepo, TokenUtil tokenUtil) {
		this.seshRepo = seshRepo;
		this.refreshRepo = refreshRepo;
		this.tokenUtil = tokenUtil;
	}
	
	public SessionTokens CreateSession(Users userID) {
		Sessions session = new Sessions();
		session.setUserID(userID);
		session.setExpAt(LocalDateTime.now().plusHours(1));
		seshRepo.save(session);
		
		String accessToken = tokenUtil.generateAccessToken(userID.getUserID());
		String refreshToken = tokenUtil.generateRefreshToken(userID.getUserID());
		SessionTokens tokens = new SessionTokens(accessToken, refreshToken);
		
		RefreshToken refresh = new RefreshToken();
		refresh.setExpAt(LocalDateTime.now().plusWeeks(1));
		refresh.setToken(refreshToken);
		refresh.setUserID(userID);
		refreshRepo.save(refresh);
		
		return tokens;
	}
	
	public boolean isRefreshTokenValid(String refresh, Long userID) {
		RefreshToken realRefresh = refreshRepo.findByUserID_UserID(userID);
		
		if(!tokenUtil.validateToken(refresh)) {
			return false;
		}else if((!refresh.equals(realRefresh.getToken()))) {
			return false;
		}
		
		return true;
		
	}
	
	@Transactional
	public Boolean CleanUpSession(Long userID) {
		refreshRepo.deleteByUserID_UserID(userID);
		seshRepo.deleteByUserID_UserID(userID);
		
		return true;
	}
}
