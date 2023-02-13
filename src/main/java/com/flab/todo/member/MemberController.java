package com.flab.todo.member;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flab.todo.common.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/sign-up")
	public ResponseEntity<Void> sendEmail(@RequestBody @Valid SignUpRequest sendEmail) {
		memberService.sendSignUpEmail(sendEmail);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/check-email-token")
	public ResponseEntity<Void> verifyEmail(@RequestParam String token, @RequestParam String email) {
		memberService.verifyEmailAndComplete(token, email);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
