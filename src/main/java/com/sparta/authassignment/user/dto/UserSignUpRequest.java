package com.sparta.authassignment.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "유효한 이메일 형식이 아닙니다.")
	private String email;

	@NotBlank(message = "아이디는 필수입니다.")
	private String username;

	@NotBlank(message = "닉네임은 필수입니다.")
	private String nickName;

	@NotBlank(message = "비밀번호는 필수입니다.")
	private String password;

	// 선택값: 관리자 등록 시 사용, 없으면 일반 유저로 가입됨
	private String secretKey;
}
