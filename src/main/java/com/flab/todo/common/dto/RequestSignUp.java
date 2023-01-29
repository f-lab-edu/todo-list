package com.flab.todo.common.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.flab.todo.database.entity.Member;

import lombok.Data;

@Data
public class RequestSignUp {

	@NotNull(message = "이메일 입력은 필수입니다.")
	@Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "올바른 이메일 형식으로 입력해주세요.")
	private String email;

	@NotBlank(message = "이름 입력은 필수입니다.")
	private String name;

	@NotEmpty(message = "비밀번호 입력은 필수입니다.")
	@Length(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
	private String password;

	@NotNull(message = "비밀번호를 옳바르게 입력해주세요.")
	private String passwordConfirm;

	public RequestSignUp(String email, String name, String password, String passwordConfirm) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}

	public static Member from(RequestSignUp requestSignup, String encryptedPassword) {
		return new Member(null, requestSignup.getEmail(), requestSignup.getName(), encryptedPassword, null);
	}
}
