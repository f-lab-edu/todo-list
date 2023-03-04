package com.flab.todo.common.config.mail;

import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class JavaMailService {

	private JavaMailSenderImpl javaMailSender;

	public JavaMailService(MailProperties mailProperties) {
		javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(mailProperties.getHost());
		javaMailSender.setUsername(mailProperties.getUsername());
		javaMailSender.setPassword(mailProperties.getPassword());
		javaMailSender.setPort(mailProperties.getPort());

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.starttls.enable", true);
		javaMailSender.setJavaMailProperties(javaMailProperties);
	}

	public void send(SimpleMailMessage mailMessage) {
		javaMailSender.send(mailMessage);
	}

}
