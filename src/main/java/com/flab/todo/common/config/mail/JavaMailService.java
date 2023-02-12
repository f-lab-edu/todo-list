package com.flab.todo.common.config.mail;

import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JavaMailService {

	private final MailProperties mailProperties;

	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(this.mailProperties.getHost());
		javaMailSender.setUsername(this.mailProperties.getUsername());
		javaMailSender.setPassword(this.mailProperties.getPassword());
		javaMailSender.setPort(this.mailProperties.getPort());

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.starttls.enable", true);
		javaMailSender.setJavaMailProperties(javaMailProperties);

		return javaMailSender;
	}

}
