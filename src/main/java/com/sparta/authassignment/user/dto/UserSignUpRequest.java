package com.sparta.authassignment.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "UserSignUpRequest", description = "회원가입 요청 DTO")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {

	@Schema(description = "유저 이름 (필수)", example = "username")
	@NotBlank(message = "유저이름은 필수입니다.")
	private String username;

	@Schema(description = "비밀번호 (필수)", example = "1234")
	@NotBlank(message = "비밀번호는 필수입니다.")
	private String password;

	@Schema(description = "닉네임 (필수)", example = "nickname")
	@NotBlank(message = "닉네임은 필수입니다.")
	private String nickName;


	@Schema(
		description = "선택값: 관리자 등록 시 사용. 없으면 일반 사용자로 가입됩니다.",
		example = "eA9!QyU3VzRvNldXp2^Wr8Lt%ZqK@Cm"
	)
	private String secretKey;
}