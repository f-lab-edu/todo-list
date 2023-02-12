package com.flab.todo.member;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flab.todo.common.config.mail.JavaMailService;
import com.flab.todo.common.config.security.UserDetailImpl;
import com.flab.todo.common.dto.SignUpRequest;
import com.flab.todo.common.exception.custom.UnAuthorizedException;
import com.flab.todo.database.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final PasswordEncoder passwordEncoder;
	private final MemberMapper memberMapper;
	private final JavaMailService javaMailService;

	public void sendSignUpEmail(SignUpRequest signUpRequest) {

		validateDuplicateUser(signUpRequest);
		validatePasswordUser(signUpRequest);

		String encryptedPassword = passwordEncoder.encode(signUpRequest.getPassword());
		Member member = SignUpRequest.from(signUpRequest, encryptedPassword);
		member.generateToken();

		sendVerificationEmailWithToken(member);

		memberMapper.save(member);
	}

	public void sendVerificationEmailWithToken(Member member) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(member.getEmail());
		mailMessage.setSubject("회원 가입 메일 인증 번호");
		String emailVerificationLink = String.format("/check-email-token?token=%s&email=%s", member.getEmailToken(),
			member.getEmail());
		mailMessage.setText(emailVerificationLink);
		javaMailService.getJavaMailSender().send(mailMessage);
		log.info("Sent verification email to: " + member.getEmail() + " with link: " + emailVerificationLink);
	}

	private void validateDuplicateUser(SignUpRequest signUpRequest) {
		boolean existsByEmail = memberMapper.existsByEmail(signUpRequest.getEmail());
		if (existsByEmail) {
			throw new IllegalArgumentException("이미 가입된 회원입니다.");
		}
	}

	private void validatePasswordUser(SignUpRequest signUpRequest) {
		if (!signUpRequest.getPassword().equals(signUpRequest.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 서로 일치하지 않습니다.");
		}
	}

	public Member findByEmailAndEmailToken(String email, String emailToken) {
		return memberMapper.findByEmailAndEmailToken(email, emailToken);
	}

	public void completeSignUp(Member member) {
		member.completeSignUp();
		memberMapper.update(member);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Member findMember = memberMapper.findByEmail(username).orElseThrow(UnAuthorizedException::new);
		return new UserDetailImpl(findMember);
	}
}
