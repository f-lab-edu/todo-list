package com.flab.todo.common.exception;

import java.util.ConcurrentModificationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flab.todo.common.exception.custom.ForbiddenRequestException;
import com.flab.todo.common.exception.custom.LoginRequiredException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// status - 400
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity validIllegalArgumentException(final IllegalArgumentException ex) {
		return ErrorResponse
			.from(HttpStatus.BAD_REQUEST, ex);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity validIllegalArgumentException(final NullPointerException ex) {
		return ErrorResponse
			.from(HttpStatus.BAD_REQUEST, ex);
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity validIndexOutOfBoundsException(final IndexOutOfBoundsException ex) {
		return ErrorResponse
			.from(HttpStatus.BAD_REQUEST, ex);
	}

	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity validUnsupportedOperationException(final UnsupportedOperationException ex) {
		return ErrorResponse
			.from(HttpStatus.BAD_REQUEST, ex);
	}

	// status - 500
	@ExceptionHandler(ConcurrentModificationException.class)
	public ResponseEntity validConcurrentModificationException(final ConcurrentModificationException ex) {
		return ErrorResponse
			.from(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity validIllegalStateException(final IllegalStateException e) {
		return ErrorResponse
			.from(HttpStatus.INTERNAL_SERVER_ERROR, e);
	}

	// custom
	@ExceptionHandler(LoginRequiredException.class)
	public ResponseEntity validLoginRequiredException(final LoginRequiredException e) {
		return ErrorResponse
			.from(HttpStatus.UNAUTHORIZED, e);
	}

	@ExceptionHandler(ForbiddenRequestException.class)
	public ResponseEntity validForbiddenRequestException(final ForbiddenRequestException e) {
		return ErrorResponse
			.from(HttpStatus.FORBIDDEN, e);
	}
}

