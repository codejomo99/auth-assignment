package com.sparta.authassignment.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserLoginRequest", description = "로그인 요청 DTO")
public class UserLoginRequest {

	@Schema(description = "유저이름 (필수)", example = "username")
	@NotBlank(message = "유저이름은 필수입니다.")
	private String username;

	@Schema(description = "비밀번호 (필수)", example = "1234")
	@NotBlank(message = "비밀번호는 필수입니다.")
	private String password;
}
