package com.matias.bingo.security;

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
		http.csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF para poder usar POST en Postman
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll() // Por ahora, permitimos todas las
																				// peticiones
				);

		return http.build();
	}
}