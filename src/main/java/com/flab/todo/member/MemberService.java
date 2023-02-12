package com.flab.todo.member;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flab.todo.common.config.mail.JavaMailService;
import com.flab.todo.common.config.mail.MailMessage;
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

	public void verifyEmailAndComplete(String token, String email){
		Member member = memberMapper.findByEmailAndEmailToken(email, token);
		validateIsExistEmail(member);
		validateIsValidToken(member, token);
		completeSignUp(member);
	}

	private void validateIsExistEmail(Member member){
		if (member == null) {
			throw new IllegalArgumentException("Email not found");
		}
	}

	private void validateIsValidToken(Member member, String token){
		if (!member.isValidToken(token)) {
			throw new IllegalArgumentException("Wrong Token");
		}
	}

	private void completeSignUp(Member member) {
		member.completeSignUp();
		memberMapper.update(member);
	}

	public void sendSignUpEmail(SignUpRequest signUpRequest) {

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
		if (!signUpRequest.getPassword().equals(signUpRequest.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 서로 일치하지 않습니다.");
		}
	}

	private void sendVerificationEmailWithToken(Member member) {
		MailMessage mailMessage = MailMessage.makeVerifyMailFrom(member);
		javaMailService.send(mailMessage);
		log.info("Sent verification email to: " + member.getEmail() + " with link: " + MailMessage.makeEmailVerificationLink(member.getEmailToken(), member.getEmail()));
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Member findMember = memberMapper.findByEmail(username).orElseThrow(UnAuthorizedException::new);
		return new UserDetailImpl(findMember);
	}
}
