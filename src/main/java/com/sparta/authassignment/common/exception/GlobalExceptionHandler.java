package com.sparta.authassignment.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<?> handleBaseException(BaseException ex) {
		BaseErrorCode errorCode = ex.getErrorCode();
		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ErrorResponse.of(errorCode.getErrorCode(), errorCode.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
		String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
		return ResponseEntity
			.badRequest()
			.body(ErrorResponse.of("VALIDATION_ERROR", errorMessage));
	}

	@Getter
	@AllArgsConstructor
	private static class ErrorResponse {
		private final ErrorDetail error;

		static ErrorResponse of(String errorCode, String message) {
			return new ErrorResponse(new ErrorDetail(errorCode,message));
		}
	}

	@Getter
	@AllArgsConstructor
	private static class ErrorDetail {
		private final String code;
		private final String message;
	}
}