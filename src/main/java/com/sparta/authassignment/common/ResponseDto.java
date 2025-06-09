package com.sparta.authassignment.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto<T> {
	private final int code;
	private final String status;
	private final String message;
	private final T data;


	// 요청이 실패했을 때 커스텀 메시지
	public static <T> ResponseDto<T> error(int code, String message) {
		return new ResponseDto<>(code, "error", message, null);
	}
}
