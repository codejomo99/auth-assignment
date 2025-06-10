package com.sparta.authassignment.user.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.authassignment.security.UserDetailsImpl;
import com.sparta.authassignment.user.dto.UserGetResponse;
import com.sparta.authassignment.user.dto.UserLoginRequest;
import com.sparta.authassignment.user.dto.UserLoginResponse;
import com.sparta.authassignment.user.dto.UserSignUpRequest;
import com.sparta.authassignment.user.dto.UserUpdateRequest;
import com.sparta.authassignment.user.entity.User;
import com.sparta.authassignment.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "User API", description = "회원가입·로그인·회원관리 API")
@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@Operation(
		summary = "회원가입",
		description = "새로운 사용자를 회원가입합니다. " +
			"secretKey는 관리자 등록 시 사용되며, 없으면 일반 사용자로 가입됩니다."
	)
	@ApiResponse(responseCode = "201", description = "회원가입 성공")
	@PostMapping("/signup")
	public ResponseEntity<Void> signUp(
		@RequestBody @Valid UserSignUpRequest requestDto
	) {
		userService.signUp(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다.")
	@ApiResponse(responseCode = "200", description = "로그인 성공, 토큰을 Authorization 헤더에 반환")
	@PostMapping("/login")
	public ResponseEntity<UserLoginResponse> login(
		@RequestBody @Valid UserLoginRequest request
	) {
		UserLoginResponse response = userService.login(request);
		return ResponseEntity
			.ok()
			.header("Authorization", response.getAccessToken())
			.body(response);
	}

	@Operation(summary = "회원 조회", description = "인증된 사용자의 정보를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "회원 정보 반환")
	@GetMapping("/api/users")
	public ResponseEntity<UserGetResponse> getUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		User user = userService.getUser(userDetails.getUser().getId());
		return ResponseEntity.ok(new UserGetResponse(user));
	}

	@Operation(summary = "회원 정보 수정", description = "인증된 사용자의 정보를 수정합니다.")
	@ApiResponse(responseCode = "200", description = "수정 완료 메시지 반환")
	@PutMapping("/api/users")
	public ResponseEntity<Map<String, String>> updateUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody UserUpdateRequest userUpdateRequest
	) {
		userService.updateUser(userDetails.getUser().getId(), userUpdateRequest);
		return ResponseEntity.ok(Collections.singletonMap("message", "사용자 정보가 수정 되었습니다."));
	}

	@Operation(summary = "회원 탈퇴", description = "인증된 사용자의 계정을 삭제합니다.")
	@ApiResponse(responseCode = "200", description = "삭제 완료 메시지 반환")
	@DeleteMapping("/api/users")
	public ResponseEntity<Map<String, String>> deleteUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		userService.deleteUser(userDetails.getUser().getId());
		return ResponseEntity.ok(Collections.singletonMap("message", "사용자 정보가 삭제 되었습니다."));
	}
}