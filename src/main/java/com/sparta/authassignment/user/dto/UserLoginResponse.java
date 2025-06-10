package com.sparta.authassignment.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserLoginResponse", description = "로그인 응답 DTO")
public class UserLoginResponse {
	private String accessToken;
}
