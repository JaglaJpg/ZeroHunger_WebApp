package com.example.zerohunger.Configuration;

import com.example.zerohunger.Service.SessionService;
import com.example.zerohunger.Utility.TokenUtil;
import com.example.zerohunger.Entity.Users;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private SessionService sessionService;

    @Value("${app.security.enabled:true}")
    private boolean securityEnabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (!securityEnabled) {
            filterChain.doFilter(request, response);
            return;
        }

        // Step 1: Extract tokens from cookies
        String accessToken = null;
        String refreshToken = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                } else if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        Long userId = null;

        // Step 2: Check access token
        if (accessToken != null && tokenUtil.validateToken(accessToken)) {
            userId = tokenUtil.extractUserId(accessToken);
        }

        // Step 3: If access token is invalid/expired → try refresh token
        else if (refreshToken != null && tokenUtil.validateToken(refreshToken)) {
            userId = tokenUtil.extractUserId(refreshToken);
            if (sessionService.isRefreshTokenValid(refreshToken, userId)) {
                // Generate a new access token
                String newAccessToken = tokenUtil.generateAccessToken(userId);

                // Set it back as a cookie
                Cookie newAccessCookie = new Cookie("accessToken", newAccessToken);
                newAccessCookie.setHttpOnly(true);
                newAccessCookie.setPath("/");
                newAccessCookie.setMaxAge(60 * 60); // 1 hour
                response.addCookie(newAccessCookie);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // Step 4: If user ID is set, store in SecurityContext
        if (userId != null) {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userId, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
