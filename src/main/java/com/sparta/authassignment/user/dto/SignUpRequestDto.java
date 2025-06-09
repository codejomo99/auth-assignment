package com.sparta.authassignment.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
	private String email;
	private String nickName;
	private String password;
}
