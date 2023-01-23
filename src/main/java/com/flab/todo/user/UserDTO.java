package com.flab.todo.user;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class UserDTO {

	@Email
	private String email;
	private String password;
	private String name;
	private String mailKey;
	private int mailAuth;

}
