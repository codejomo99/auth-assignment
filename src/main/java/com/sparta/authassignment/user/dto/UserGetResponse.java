package com.sparta.authassignment.user.dto;

import com.sparta.authassignment.user.entity.User;
import com.sparta.authassignment.user.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserGetResponse {
	private String username;
	private String nickName;
	private UserRole role;

	public UserGetResponse(User user) {
		this.username = user.getUsername();
		this.nickName = user.getNickName();
		this.role = user.getUserRole();
	}
}

