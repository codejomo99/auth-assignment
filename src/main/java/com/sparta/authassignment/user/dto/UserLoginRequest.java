package com.sparta.authassignment.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

	@Schema(description = "이메일 (필수)", example = "admin@example.com")
	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "유효한 이메일 형식이 아닙니다.")
	private String email;

	@Schema(description = "비밀번호 (필수)", example = "1234")
	@NotBlank(message = "비밀번호는 필수입니다.")
	private String password;
}
