package com.flab.todo.common.exception.custom;

public class LoginRequiredException extends Exception{
	public LoginRequiredException() {
		super("Login required");
	}
}
