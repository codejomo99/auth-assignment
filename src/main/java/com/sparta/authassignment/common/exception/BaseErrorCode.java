package com.sparta.authassignment.common.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

	String getErrorCode();
	String getMessage();
	HttpStatus getStatus();
}
