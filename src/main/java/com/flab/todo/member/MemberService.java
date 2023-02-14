package com.flab.todo.member;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flab.todo.common.config.mail.JavaMailService;
import com.flab.todo.common.config.mail.MailMessageMaker;
import com.flab.todo.common.dto.SignUpRequest;
import com.flab.todo.database.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberMapper memberMapper;
	private final JavaMailService javaMailService;

	public void completeSignUp(String token, String email) {
		Member member = memberMapper.findByEmailAndEmailToken(email, token)
			.orElseThrow(() -> new IllegalArgumentException("Email not found"));
		validateIsValidToken(member, token);
		member.completeSignUp();
		memberMapper.update(member);
	}

	private void validateIsValidToken(Member member, String token) {
		if (!member.isValidToken(token)) {
			throw new IllegalArgumentException("Wrong Token");
		}
	}

	public void requestVerificationToken(SignUpRequest signUpRequest) {

		validateDuplicateUser(signUpRequest);
		validatePassword(signUpRequest);

		String encryptedPassword = passwordEncoder.encode(signUpRequest.getPassword());
		Member member = SignUpRequest.from(signUpRequest, encryptedPassword);
		member.generateToken();

		sendVerificationEmailWithToken(member);

		memberMapper.save(member);
	}

	private void validateDuplicateUser(SignUpRequest signUpRequest) {
		boolean existsByEmail = memberMapper.existsByEmail(signUpRequest.getEmail());
		if (existsByEmail) {
			throw new IllegalArgumentException("이미 가입된 회원입니다.");
		}
	}

	private void validatePassword(SignUpRequest signUpRequest) {
		if (!signUpRequest.isValidPassword()) {
			throw new IllegalArgumentException("비밀번호가 서로 일치하지 않습니다.");
		}
	}

	private void sendVerificationEmailWithToken(Member member) {
		SimpleMailMessage mailMessage = MailMessageMaker.makeVerifyMailFrom(member);
		javaMailService.send(mailMessage);
		log.info("Sent verification email to: " + member.getEmail() + " with link: "
			+ MailMessageMaker.makeEmailVerificationLink(member.getEmailToken(), member.getEmail()));
	}
}
