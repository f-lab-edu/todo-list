package com.flab.todo.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberMapper memberMapper;

	// public Long signUp(Member member) {
	// 	validateDuplicateUser(member);
	// 	validatePassword(member);
	//
	// 	// encrypt password
	// 	String encryptedPassword = passwordEncoder.encode(requestSignup.getPassword());
	//
	// 	// convert RequestSignup to Member
	// 	Member member = RequestSignUp.from(requestSignup, encryptedPassword);
	// 	return memberMapper.insert(member);
	// }
	//
	// private void validateDuplicateUser(Member member) {
	// 	Member findMember = memberMapper.findByEmail(member.getEmail());
	// 	if (findMember != null) {
	// 		throw new IllegalArgumentException("이미 가입된 회원입니다.");
	// 	}
	// }
	//
	// private void validatePassword(Member member) {
	// 	if (!requestSignup.getPassword().equals(requestSignup.getPasswordConfirm())) {
	// 		throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
	// 	}
	// }
}
