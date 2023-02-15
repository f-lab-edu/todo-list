package com.flab.todo.common.event;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.flab.todo.common.config.mail.JavaMailService;
import com.flab.todo.common.config.mail.MailMessageMaker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VerificationEmailListener {

	private final JavaMailService javaMailService;

	@EventListener
	public void handleVerificationEmailEvent(VerificationEmailEvent event) {
		SimpleMailMessage mailMessage = event.getMailMessage();
		javaMailService.send(mailMessage);
		log.info("Sent verification email to: " + mailMessage.getTo()[0] + " with link: "
			+ MailMessageMaker.makeEmailVerificationLink(mailMessage.getText(), mailMessage.getTo()[0]));
	}
}
