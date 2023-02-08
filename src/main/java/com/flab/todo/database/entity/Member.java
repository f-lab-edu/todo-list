package com.flab.todo.database.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

	private Long id;
	private String email;
	private String name;
	private String password;
	private String emailToken;
	private LocalDateTime joinedAt;
	private LocalDateTime emailTokenGeneratedAt;
	private LocalDateTime tokenExpiration;
	private boolean isValid = false;

	public void generateToken() {
		this.emailToken = UUID.randomUUID().toString();
		this.emailTokenGeneratedAt = LocalDateTime.now();
	}

	public boolean isValidToken(String token) {
		return this.emailToken.equals(token);
	}

	public void completeSignUp() {
		this.isValid = true;
		joinedAt = LocalDateTime.now();
	}
}
