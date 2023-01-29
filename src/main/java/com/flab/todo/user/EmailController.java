package com.flab.todo.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmailController {

	private final MailService mailService;

	@PostMapping("/send-email")
	public String mailConfirm(@RequestParam String email) throws Exception {
		String authCode = mailService.sendSimpleMessage(email);
		log.info("인증코드 : " + authCode);
		return authCode;
	}
}
