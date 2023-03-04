package com.flab.todo.common.exception.custom;

public class UnAuthorizedException extends RuntimeException {
	public UnAuthorizedException() {
		super("Unauthorized Request");
	}
}
