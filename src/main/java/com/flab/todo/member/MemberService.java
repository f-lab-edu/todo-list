package com.flab.todo.member;

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
	public void SignUp(RequestSignUp requestSignUp) {

		Member member = saveNewSignUp(requestSignUp);
		validateDuplicateUser(requestSignUp);
		validatePasswordUser(requestSignUp);
		member.generateToken();
		sendVerificationEmail(member);
		memberMapper.save(member);
	}

	private Member saveNewSignUp(RequestSignUp requestSignUp) {
		String encryptedPassword = passwordEncoder.encode(requestSignUp.getPassword());
		return RequestSignUp.from(requestSignUp, encryptedPassword);
	}

	private void sendVerificationEmail(Member member) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(member.getEmail());
		mailMessage.setSubject("회원 가입 메일 인증 번호");
		mailMessage.setText(
			String.format("/check-email-token?token=%s&email=%s", member.getEmailToken(), member.getEmail()));
		javaMailSender.send(mailMessage);
	}

	public void validateDuplicateUser(RequestSignUp requestSignUp) {
		Member memberEmail = memberMapper.findByEmail(requestSignUp.getEmail());
		if (memberEmail != null && memberEmail.isValid()) {
			throw new IllegalArgumentException("이미 가입된 회원입니다.");
		}
	}
	
	public void validatePasswordUser(RequestSignUp requestSignUp) {
		if (!requestSignUp.getPassword().equals(requestSignUp.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 서로 일치하지 않습니다.");
		}
	}

	public Member findByEmail(String email) {
		return memberMapper.findByEmail(email);
	}

	public boolean isValidToken(String token) {
		Member member = memberMapper.findByEmail(token);
		if (member == null) {
			return false;
		}
		return member.isValidToken(token);
	}

	public void completeSignUp(Member member) {
		member.completeSignUp();
	}
}
