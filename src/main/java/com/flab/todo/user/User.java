package com.flab.todo.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User {
	private Long id;
	private String email;
	private String password;
	private String name;
	private String mailKey;
	private int mailAuth;

	public User(String email, String password, String name, String mailKey, int mailAuth) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.mailKey = mailKey;
		this.mailAuth = mailAuth;
	}
}
