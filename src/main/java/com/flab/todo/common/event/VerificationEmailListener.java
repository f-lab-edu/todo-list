package com.flab.todo.common.event;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.flab.todo.common.config.mail.JavaMailService;
import com.flab.todo.common.config.mail.MailMessageMaker;
import com.flab.todo.database.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VerificationEmailListener {

	private final JavaMailService javaMailService;

	@EventListener
	public void handleVerificationEmailEvent(VerificationEmailEvent event) {
		Member member = event.getMember();
		SimpleMailMessage mailMessage = MailMessageMaker.makeVerifyMailFrom(member);
		javaMailService.send(mailMessage);
		log.info("Sent verification email to: " + member.getEmail() + " with link: "
			+ MailMessageMaker.makeEmailVerificationLink(member.getEmailToken(), member.getEmail()));
	}
}
