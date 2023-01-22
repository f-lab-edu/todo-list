package com.flab.todo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sun.tools.jconsole.JConsoleContext;

@RestControllerAdvice
public class ExceptionHandlers {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<RestApiException> handleCustomException(CustomException e){
		RestApiException restApiException = RestApiException.of(e.getErrorCode());
		return new ResponseEntity<>(restApiException, restApiException.getStatusCode());
	}

	@ExceptionHandler({ Exception.class })
	protected ResponseEntity handleServerException(Exception ex) {
		return new ResponseEntity<>(new RestApiException(ex.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
