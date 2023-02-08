package com.flab.todo.common.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
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

	@NotBlank(message = "비밀번호확인 입력은 필수입니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 최소8자 이상, 문자1개, 숫자1개, 특수문자 1개 포함입니다.")
	private String password;

	@NotBlank(message = "비밀번호확인 입력은 필수입니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 최소8자 이상, 문자1개, 숫자1개, 특수문자 1개 포함입니다.")
	private String passwordConfirm;

	public static Member from(RequestSignUp requestSignup, String encryptedPassword) {
		return new Member(null, requestSignup.getEmail(), requestSignup.getName(), encryptedPassword,
			null, null, null, null, false);
	}
}
