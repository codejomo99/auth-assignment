package com.sparta.authassignment.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sparta.authassignment.common.exception.CommonErrorCode;
import com.sparta.authassignment.common.exception.CustomAuthenticationException;
import com.sparta.authassignment.security.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationEntryPoint authenticationEntryPoint;

	public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, AuthenticationEntryPoint authenticationEntryPoint) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
		String requestURI = req.getRequestURI();

		// 인증 필요 없는 URL은 JWT 필터 패스
		if (requestURI.startsWith("/signup") || requestURI.startsWith("/login") || requestURI.startsWith("/h2-console")) {
			filterChain.doFilter(req, res);
			return;
		}

		try {
			String tokenValue = jwtUtil.getJwtFromHeader(req);

			if (!StringUtils.hasText(tokenValue)) {
				log.info("JWT 토큰이 존재하지 않음");
				throw new CustomAuthenticationException(CommonErrorCode.JWT_MISSING);
			}

			log.info("JWT 토큰 추출 성공: {}", tokenValue);

			if (!jwtUtil.validateToken(tokenValue)) {
				log.error("JWT 토큰 유효성 검증 실패");
				throw new CustomAuthenticationException(CommonErrorCode.JWT_INVALID);
			}

			Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
			log.info("토큰에서 추출한 이메일: {}", info.getSubject());

			setAuthentication(info.getSubject());

			filterChain.doFilter(req, res);
		} catch (CustomAuthenticationException ex) {
			authenticationEntryPoint.commence(req, res, ex);
		}
	}

	// 인증 처리
	public void setAuthentication(String email) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);

		log.info("인증 객체 생성 성공 - 사용자: {}", email);
		log.info("사용자 권한 목록: {}", userDetails.getAuthorities());

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(email);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	// 인증 객체 생성
	private Authentication createAuthentication(String email) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}