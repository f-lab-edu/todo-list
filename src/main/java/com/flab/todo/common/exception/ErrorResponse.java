package com.flab.todo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private int status;
	private String message;
	private String error;

	public ErrorResponse(int status, String message, String error) {
		this.status = status;
		this.message = message;
		this.error = error;
	}

	public static ResponseEntity from(HttpStatus httpStatus, Exception ex){
		ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), httpStatus.name(), ex.getMessage());

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(errorResponse);
	}
}