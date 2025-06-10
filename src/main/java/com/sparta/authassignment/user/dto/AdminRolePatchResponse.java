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
@Schema(name = "AdminRolePatchResponse", description = "관리자 권한 변경시 반환 DTO")
public class AdminRolePatchResponse {
	private String username;
	private String nickname;
	private RoleResponse roles;

	public AdminRolePatchResponse(User findUser) {
		this.username = findUser.getUsername();
		this.nickname = findUser.getNickName();
		this.roles = new RoleResponse(findUser.getUserRole());
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RoleResponse {
		private UserRole role;
	}
}
