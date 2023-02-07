package com.flab.todo.common.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.flab.todo.database.entity.Member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestSignUp {

	@NotBlank(message = "이메일 입력은 필수입니다.")
	@Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "올바른 이메일 형식으로 입력해주세요.")
	private String email;

	@NotBlank(message = "이름 입력은 필수입니다.")
	private String name;

	@NotBlank(message = "비밀번호 입력은 필수입니다.")
	@Length(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
	private String password;

	@NotBlank(message = "비밀번호확인 입력은 필수입니다.")
	@Length(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
	private String passwordConfirm;

	public static Member from(RequestSignUp requestSignup, String encryptedPassword) {
		return new Member(null, requestSignup.getEmail(), requestSignup.getName(), encryptedPassword,
			null, null, null, false);
	}
}
