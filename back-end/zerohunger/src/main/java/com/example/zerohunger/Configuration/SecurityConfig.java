package com.example.zerohunger.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	@Value("${app.security.enabled:true}")
	private boolean securityEnabled;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable());

	    // ✅ Always allow H2 Console
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/h2-console/**").permitAll()
	        )
	        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

	    if (!securityEnabled) {
	        http
	            .authorizeHttpRequests(auth -> auth
	                .anyRequest().permitAll()
	            );
	    } else {
	        http
	            .cors(withDefaults()) // ✅ ADD THIS
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/auth/**").permitAll()
	                .anyRequest().authenticated()
	            )
	            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	    }

	    return http.build();
	}




}
