package com.flab.todo.database.entity;

import java.time.LocalDateTime;
import java.util.Objects;
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
	private boolean isValid;

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

	@Override
	public String toString() {
		return "Member{" +
			"id=" + id +
			", email='" + email + '\'' +
			", name='" + name + '\'' +
			", password='" + password + '\'' +
			", emailToken='" + emailToken + '\'' +
			", joinedAt=" + joinedAt +
			", emailTokenGeneratedAt=" + emailTokenGeneratedAt +
			", isValid=" + isValid +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Member member = (Member)o;
		return isValid == member.isValid && Objects.equals(id, member.id) && Objects.equals(email,
			member.email) && Objects.equals(name, member.name) && Objects.equals(password,
			member.password) && Objects.equals(emailToken, member.emailToken) && Objects.equals(
			joinedAt, member.joinedAt) && Objects.equals(emailTokenGeneratedAt, member.emailTokenGeneratedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email, name, password, emailToken, joinedAt, emailTokenGeneratedAt,
			isValid);
	}
}
