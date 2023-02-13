package com.flab.todo.common.config.mail;

import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaMailService {

	private final MailProperties mailProperties;
	private JavaMailSenderImpl javaMailSender;
	// Singleton - life cycle => requests 들어올때마다 만들어져야하는지? 한번 만든걸 재활용할 수 있을지??

	public JavaMailService(MailProperties mailProperties) {
		this.mailProperties = mailProperties;

		javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(this.mailProperties.getHost());
		javaMailSender.setUsername(this.mailProperties.getUsername());
		javaMailSender.setPassword(this.mailProperties.getPassword());
		javaMailSender.setPort(this.mailProperties.getPort());

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.starttls.enable", true);
		javaMailSender.setJavaMailProperties(javaMailProperties);
	}

	public void send(MailMessage mailMessage) {
		javaMailSender.send(mailMessage);
	}

}
