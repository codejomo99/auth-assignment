package com.sparta.authassignment.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.authassignment.common.exception.CommonErrorCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json;charset=UTF-8");

		CommonErrorCode errorCode = CommonErrorCode.ACCESS_DENIED;

		Map<String, Object> errorResponse = new HashMap<>();
		Map<String, String> error = new HashMap<>();
		error.put("code", errorCode.getErrorCode());
		error.put("message", errorCode.getMessage());
		errorResponse.put("error", error);

		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}
}
