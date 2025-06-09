package com.sparta.authassignment.common.exception;



import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

@Getter
public class CustomAuthenticationException extends AuthenticationException {

	private final CommonErrorCode errorCode;

	public CustomAuthenticationException(CommonErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
