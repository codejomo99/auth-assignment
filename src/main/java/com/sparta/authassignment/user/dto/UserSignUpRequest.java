package com.sparta.authassignment.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {
	private String email;
	private String username;
	private String nickName;
	private String password;
	private String secretKey;
}
