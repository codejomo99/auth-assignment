package com.sparta.authassignment.user.dto;

import com.sparta.authassignment.user.entity.User;
import com.sparta.authassignment.user.entity.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserSignUpResponse", description = "회원가입 응답 DTO")
public class UserSignUpResponse {
	private String username;
	private String nickname;

	private RoleResponse roles;

	public UserSignUpResponse(User user) {
		this.username = user.getUsername();
		this.nickname = user.getNickName();
		this.roles = new RoleResponse(user.getUserRole());
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RoleResponse {
		private UserRole role;
	}
}
