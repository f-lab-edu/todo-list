package com.flab.todo.common.event;

import org.springframework.mail.SimpleMailMessage;

import lombok.Getter;

@Getter
public class VerificationEmailEvent {

	private final SimpleMailMessage mailMessage;

	public VerificationEmailEvent(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
}
