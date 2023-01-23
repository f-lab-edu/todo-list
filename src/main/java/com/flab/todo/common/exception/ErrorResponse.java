package com.flab.todo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public enum ErrorResponse {

	REQUIRED_LOGIN_ERROR(HttpStatus.BAD_REQUEST, "로그인이 필요합니다.");

	private ResponseEntity responseEntity;

	ErrorResponse(HttpStatus status, String message) {
		this.responseEntity = new ResponseEntity(message, status);
	}
};
