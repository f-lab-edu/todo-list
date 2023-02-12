package com.flab.todo.common.config.mail;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("spring.mail")
public class MailProperties {

	private String host;
	private int port;
	private String username;
	private String password;
	private Properties properties;
}
