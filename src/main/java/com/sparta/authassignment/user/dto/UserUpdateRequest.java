package com.sparta.authassignment.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserUpdateRequest", description = "유저 업데이트 요청 DTO")
public class UserUpdateRequest {

	@Schema(description = "유저 닉네임", example = "newNickName")
	private String nickName;
}
