package com.flab.todo.member;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flab.todo.common.dto.RequestSignUp;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("sign-up")
	public ResponseEntity<String> signUp(@Valid @RequestBody RequestSignUp requestSignUp, Errors errors) {
		memberService.signUp(requestSignUp);
		return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
	}
}
