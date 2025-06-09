package com.sparta.authassignment.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.authassignment.common.exception.CommonErrorCode;
import com.sparta.authassignment.common.exception.CustomAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		response.setContentType("application/json;charset=UTF-8");

		CommonErrorCode errorCode;

		if (authException instanceof CustomAuthenticationException customEx) {
			errorCode = customEx.getErrorCode();
		} else {
			errorCode = CommonErrorCode.UNAUTHORIZED;
		}

		response.setStatus(errorCode.getStatus().value());

		Map<String, Object> errorResponse = new HashMap<>();
		Map<String, String> error = new HashMap<>();
		error.put("code", errorCode.getErrorCode());
		error.put("message", errorCode.getMessage());
		errorResponse.put("error", error);

		new ObjectMapper().writeValue(response.getWriter(), errorResponse);
	}
}