package com.flab.todo.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestApiException {
	private String msg;
	private String code;
	private HttpStatus statusCode;

	public RestApiException(String msg, String code, Integer statusCode) {
		this.msg = msg;
		this.code = code;
		this.statusCode = HttpStatus.valueOf(statusCode);
	}

	public RestApiException(ErrorCode errorCode){
		this.msg = errorCode.getMessage();
		this.code = errorCode.getCode();
		this.statusCode = HttpStatus.valueOf(errorCode.getStatus());
	}

	public static RestApiException of(ErrorCode errorCode){
		return new RestApiException(errorCode);
	}
}