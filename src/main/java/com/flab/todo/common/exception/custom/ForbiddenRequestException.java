package com.flab.todo.common.exception.custom;

public class ForbiddenRequestException extends Exception {
	public ForbiddenRequestException() {
		super("This Request is Forbidden");
	}
}
