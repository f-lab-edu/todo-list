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
import com.flab.todo.database.entity.Member;

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
	public ResponseEntity<String> verifyEmail(@RequestParam String token, @RequestParam String email) {

		Member member = memberService.findByEmailAndEmailToken(email, token);
		if (member == null) {
			throw new IllegalArgumentException("Email not found");
		}

		if (!member.isValidToken(token)) {
			throw new IllegalArgumentException("Wrong Token");
		}

		memberService.completeSignUp(member);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
