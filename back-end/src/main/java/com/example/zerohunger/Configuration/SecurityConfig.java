package com.example.zerohunger.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()  // Allow H2 Console
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())  // Disable CSRF for H2 Console
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // Allow frames

        return http.build();
    }
}
