package com.flab.todo.member;

import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flab.todo.common.dto.RequestSignUp;
import com.flab.todo.database.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberMapper memberMapper;
	private final JavaMailSender javaMailSender;

	@Transactional
	public void sendSignUpEmail(RequestSignUp requestSignUp) {

		validateDuplicateUser(requestSignUp);
		validatePasswordUser(requestSignUp);

		String encryptedPassword = passwordEncoder.encode(requestSignUp.getPassword());
		Member member = RequestSignUp.from(requestSignUp, encryptedPassword);
		member.generateToken();

		sendVerificationEmailWithToken(member);

		memberMapper.save(member);
	}

	private void sendVerificationEmailWithToken(Member member) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(member.getEmail());
		mailMessage.setSubject("회원 가입 메일 인증 번호");
		mailMessage.setText(
			String.format("/check-email-token?token=%s&email=%s", member.getEmailToken(), member.getEmail()));
		javaMailSender.send(mailMessage);
	}

	public void validateDuplicateUser(RequestSignUp requestSignUp) {
		Optional<Member> memberEmail = memberMapper.findByEmail(requestSignUp.getEmail());
		if (memberEmail.isPresent()) {
			throw new IllegalArgumentException("이미 가입된 회원입니다.");
		}
	}

	public void validatePasswordUser(RequestSignUp requestSignUp) {
		if (!requestSignUp.getPassword().equals(requestSignUp.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 서로 일치하지 않습니다.");
		}
	}

	public Member findByEmailAndEmailToken(String email, String emailToken) {
		return memberMapper.findByEmailAndEmailToken(email, emailToken);
	}

	@Transactional
	public void completeSignUp(Member member) {
		member.completeSignUp();
		memberMapper.update(member);
	}
}