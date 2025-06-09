package com.sparta.authassignment.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.authassignment.user.dto.UserLoginRequest;
import com.sparta.authassignment.user.dto.UserSignUpRequest;
import com.sparta.authassignment.user.repository.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;  // JSON 직렬화/역직렬화

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void cleanup() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("회원가입이 성공했습니다.")
	void signUp_Success() throws Exception {
		// given
		UserSignUpRequest request = new UserSignUpRequest("test@test.com", "nickname", "nickname");
		String json = objectMapper.writeValueAsString(request);

		// when & then
		mockMvc.perform(post("/api/users/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isCreated());  // 201
	}

	@Test
	@DisplayName("로그인 성공 시 200 OK, Authorization 헤더에 JWT 토큰이 담겨옵니다")
	void login_Success_ReturnsToken() throws Exception {
		// 먼저 회원가입
		mockMvc.perform(post("/api/users/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
					new UserSignUpRequest("test@test.com", "nickname", "1234")
				)))
			.andExpect(status().isCreated());

		// 로그인 요청
		UserLoginRequest loginDto = new UserLoginRequest("test@test.com", "1234");
		String loginBody = objectMapper.writeValueAsString(loginDto);

		mockMvc.perform(post("/api/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginBody))
			.andExpect(status().isOk())
			.andExpect(header().exists("Authorization"))
			.andExpect(jsonPath("$.accessToken").isNotEmpty());
	}

}