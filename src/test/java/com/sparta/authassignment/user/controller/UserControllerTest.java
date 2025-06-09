package com.sparta.authassignment.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.authassignment.common.exception.CommonErrorCode;
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

	@Value("${admin.signup.secret-key}")
	private String adminSecretKey;

	@BeforeEach
	void cleanup() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("유저 회원가입이 성공했습니다.")
	void signUp_User_Success() throws Exception {
		// given
		UserSignUpRequest request = new UserSignUpRequest("test@test.com", "nickname", "nickname",adminSecretKey);
		String json = objectMapper.writeValueAsString(request);

		// when-then
		mockMvc.perform(post("/api/users/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isCreated());  // 201
	}

	@Test
	@DisplayName("관리자 회원가입이 성공했습니다.")
	void signUp_Admin_Fail() throws Exception {
		// given
		UserSignUpRequest request = new UserSignUpRequest("test@test.com", "nickname", "nickname",null);
		String json = objectMapper.writeValueAsString(request);

		// when-the
		mockMvc.perform(post("/api/users/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isCreated());  // 201
	}

	@Test
	@DisplayName("관리자 회원가입이 실패했습니다.")
	void signUp_Admin_Success() throws Exception {

		// given
		UserSignUpRequest request = new UserSignUpRequest("test@test.com", "nickname", "nickname","secretKey");
		String json = objectMapper.writeValueAsString(request);

		// when-then
		mockMvc.perform(post("/api/users/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("로그인 성공 시 200 OK, Authorization 헤더에 JWT 토큰이 담겨옵니다")
	void login_Success_ReturnsToken() throws Exception {
		// 먼저 회원가입
		mockMvc.perform(post("/api/users/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
					new UserSignUpRequest("test@test.com", "nickname", "1234",null)
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

	@Test
	@DisplayName("로그인 실패 시 INVALID_CREDENTIALS 에러 응답 반환")
	void login_Fail_InvalidCredentials() throws Exception {
		// 회원가입
		mockMvc.perform(post("/api/users/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
					new UserSignUpRequest("test@test.com", "nickname", "1234",null)
				)))
			.andExpect(status().isCreated());

		// 잘못된 비밀번호로 로그인 시도
		UserLoginRequest wrongLogin = new UserLoginRequest("test@test.com", "1235");
		String wrongJson = objectMapper.writeValueAsString(wrongLogin);

		mockMvc.perform(post("/api/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(wrongJson))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.error.code").value(CommonErrorCode.INVALID_CREDENTIALS.getErrorCode()))
			.andExpect(jsonPath("$.error.message").value(CommonErrorCode.INVALID_CREDENTIALS.getMessage()));
	}


}