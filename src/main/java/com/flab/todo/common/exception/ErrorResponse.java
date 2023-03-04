package com.flab.todo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private int status;
	private String message;

	public ErrorResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public static ResponseEntity from(HttpStatus httpStatus, Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), ex.getMessage());

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(errorResponse);
	}

	public static ResponseEntity from(HttpStatus httpStatus, String message) {
		ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(errorResponse);
	}
}
