package com.flab.todo.common.config.mail;

import org.springframework.mail.SimpleMailMessage;

import com.flab.todo.database.entity.Member;

public class MailMessageMaker {

	public static SimpleMailMessage makeVerifyMailFrom(Member member) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(member.getEmail());
		mailMessage.setSubject("회원 가입 메일 인증 번호");
		String emailVerificationLink = makeEmailVerificationLink(member.getEmailToken(), member.getEmail());
		mailMessage.setText(emailVerificationLink);
		return mailMessage;
	}

	public static String makeEmailVerificationLink(String token, String email) {
		return String.format("/check-email-token?token=%s&email=%s", token, email);
	}
}
