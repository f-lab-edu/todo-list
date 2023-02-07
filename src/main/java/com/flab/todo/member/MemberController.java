package com.flab.todo.member;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flab.todo.common.dto.RequestSignUp;
import com.flab.todo.database.entity.Member;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/sign-up")
	public ResponseEntity<Void> signUp(@RequestBody @Valid RequestSignUp SignUp, Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		memberService.SignUp(SignUp);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/check-email-token")
	public ResponseEntity<String> verifyEmail(@RequestParam String token, @RequestParam String email) {

		Member memberByEmail = memberService.findByEmail(email);
		if (memberByEmail == null) {
			return new ResponseEntity<>("wrong email", HttpStatus.BAD_REQUEST);
		}

		if (!memberService.isValidToken(token)) {
			return new ResponseEntity<>("Wrong Token", HttpStatus.BAD_REQUEST);
		}

		memberService.completeSignUp(memberByEmail);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
