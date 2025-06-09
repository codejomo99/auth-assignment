package com.sparta.authassignment.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements BaseErrorCode {

	USER_NOT_EXISTS("USER_NOT_EXISTS","존재하지 않은 사용자입니다.", HttpStatus.NOT_FOUND),

	// 회원가입 시 이미 존재하는 사용자
	USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "이미 가입된 사용자입니다.", HttpStatus.CONFLICT), //

	// 로그인 실패 (자격 증명 오류)
	INVALID_CREDENTIALS("INVALID_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED),

	UNAUTHORIZED("UNAUTHORIZED", "로그인이 필요한 요청입니다. 인증에 실패했습니다.", HttpStatus.UNAUTHORIZED),

	// 추가된 JWT 관련 에러들
	JWT_MISSING("JWT_MISSING", "JWT 토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
	JWT_INVALID("JWT_INVALID", "유효하지 않은 인증 토큰입니다.", HttpStatus.UNAUTHORIZED),
	JWT_AUTHENTICATION_FAILED("JWT_AUTHENTICATION_FAILED", "JWT 인증에 실패했습니다.", HttpStatus.UNAUTHORIZED),

	// 인증은 되었지만 권한이 없는 경우
	ACCESS_DENIED("ACCESS_DENIED", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

	// 관리자 가입 실패
	ACCESS_DENIED_ADMIN("ACCESS_DENIED_ADMIN","관리자 가입에 실패했습니다.", HttpStatus.FORBIDDEN)

	;

	private final String errorCode;
	private final String message;
	private final HttpStatus status;
}
