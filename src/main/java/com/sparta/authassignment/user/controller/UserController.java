package com.sparta.authassignment.user.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.authassignment.user.dto.UserLoginRequest;
import com.sparta.authassignment.user.dto.UserLoginResponse;
import com.sparta.authassignment.user.dto.UserSignUpRequest;
import com.sparta.authassignment.user.dto.UserGetResponse;
import com.sparta.authassignment.user.dto.UserUpdateRequest;
import com.sparta.authassignment.user.entity.User;
import com.sparta.authassignment.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/sign-up")
	public ResponseEntity<Void> signUp(
		@RequestBody UserSignUpRequest requestDto
	) {
		userService.signUp(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/login")
	public ResponseEntity<UserLoginResponse> login(
		@RequestBody UserLoginRequest request
	){

		UserLoginResponse response = userService.login(request);
		return ResponseEntity.ok().header("Authorization", response.getAccessToken())
			.body(response);
	}


	//TODO: 추후 email 기준으로 변경 할 예정

	@GetMapping("/{id}")
	public ResponseEntity<UserGetResponse> getUser(
		@PathVariable Long id
	) {
		User user = userService.getUser(id);
		UserGetResponse response = new UserGetResponse(user);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, String>> updateUser(@PathVariable Long id,
		@RequestBody UserUpdateRequest userUpdateRequest){

		userService.updateUser(id,userUpdateRequest);
		return ResponseEntity.ok(Collections.singletonMap("message", "사용자 정보가 수정 되었습니다."));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.ok(Collections.singletonMap("message", "사용자 정보가 삭제 되었습니다."));
	}

}
