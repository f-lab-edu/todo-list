package com.flab.todo.common.exception.custom;

public class ForbiddenRequestException extends RuntimeException {
	public ForbiddenRequestException() {
		super("This Request is Forbidden");
	}
}
