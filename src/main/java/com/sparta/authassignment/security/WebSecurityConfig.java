package com.sparta.authassignment.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sparta.authassignment.security.jwt.JwtAuthorizationFilter;
import com.sparta.authassignment.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {


	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final CustomAccessDeniedHandler accessDeniedHandler;


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.headers(headers ->
				headers.frameOptions(frame -> frame.disable())  // H2 콘솔이 iframe으로 뜰 수 있게
			)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(
					"/h2-console/**",
					"/signup",
					"/login"
				).permitAll()
				.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated()
			)
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.exceptionHandling(exception -> exception
				.accessDeniedHandler(accessDeniedHandler)
			)
		;

		http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}


}
