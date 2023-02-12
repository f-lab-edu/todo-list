package com.flab.todo.member;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
	//
	// @Autowired
	// private MemberService memberService;

	// @Autowired
	// private MemberMapper memberMapper;
	//
	// @Autowired
	// private PasswordEncoder passwordEncoder;
	//
	// @Autowired
	// private JavaMailService javaMailService;
	//
	// @Test
	// @DisplayName("1. 회원 가입 - 성공")
	// void case1() {
	// 	// given
	// 	SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
	// 		"12345678!q2");
	//
	// 	given(memberMapper.existsByEmail("cjyeon1022@gmail.com")).willReturn(false);
	// 	String encryptedPassword = passwordEncoder.encode(signUpRequest.getPassword());
	//
	// 	// when
	// 	memberService.sendSignUpEmail(signUpRequest);
	//
	// 	// then
	// 	verify(memberMapper, times(1)).save(
	// 		new Member(signUpRequest.getEmail(), signUpRequest.getName(), encryptedPassword));
	// 	verify(javaMailService, times(1)).sendVerificationEmailWithToken(any(Member.class));
	// }
}