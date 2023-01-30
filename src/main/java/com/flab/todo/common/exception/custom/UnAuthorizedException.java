package com.flab.todo.common.exception.custom;

public class UnAuthorizedException extends Exception {
	public UnAuthorizedException() {
		super("Unauthorized Request");
	}
}
