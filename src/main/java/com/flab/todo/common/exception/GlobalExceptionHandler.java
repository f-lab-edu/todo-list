package com.flab.todo.common.exception;

import java.util.ConcurrentModificationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flab.todo.common.exception.custom.ForbiddenRequestException;
import com.flab.todo.common.exception.custom.UnAuthorizedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// status - 400
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity validIllegalArgumentException(final IllegalArgumentException ex) {
		log.error("validIllegalArgumentException", ex);
		return ErrorResponse
			.from(HttpStatus.BAD_REQUEST, ex);
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity validIndexOutOfBoundsException(final IndexOutOfBoundsException ex) {
		log.error("validIndexOutOfBoundsException", ex);
		return ErrorResponse
			.from(HttpStatus.BAD_REQUEST, ex);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity validMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
		log.error("validMethodArgumentNotValidException", ex);
		return ErrorResponse
			.from(HttpStatus.BAD_REQUEST, ex.getBindingResult().getFieldError().getDefaultMessage()); // enum
	}

	// status - 500
	@ExceptionHandler(ConcurrentModificationException.class)
	public ResponseEntity validConcurrentModificationException(final ConcurrentModificationException ex) {
		log.error("ConcurrentModificationException", ex);
		return ErrorResponse
			.from(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity validIllegalStateException(final IllegalStateException ex) {
		log.error("validIllegalStateException", ex);
		return ErrorResponse
			.from(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity validNullPointerException(final NullPointerException ex) {
		log.error("NullPointerException", ex);
		return ErrorResponse
			.from(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity validUnsupportedOperationException(final UnsupportedOperationException ex) {
		log.error("UnsupportedOperationException", ex);
		return ErrorResponse
			.from(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity validRuntimeException(final RuntimeException ex) {
		log.error("ValidRuntimeException", ex);
		return ErrorResponse
			.from(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	// custom
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity validLoginRequiredException(final UnAuthorizedException ex) {
		log.error("UnAuthorizedException", ex);
		return ErrorResponse
			.from(HttpStatus.UNAUTHORIZED, ex);
	}

	@ExceptionHandler(ForbiddenRequestException.class)
	public ResponseEntity validForbiddenRequestException(final ForbiddenRequestException ex) {
		log.error("ForbiddenRequestException", ex);
		return ErrorResponse
			.from(HttpStatus.FORBIDDEN, ex);
	}
}
