package com.flab.todo.member;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	MemberService memberService;

	@Mock
	MemberMapper memberMapper;

	@Mock
	PasswordEncoder passwordEncoder;

	// TODO ING~
	// @Test
	// @DisplayName("Save Member - Correct Validation")
	// void case1() throws Exception {
	// 	// given
	// 	RequestSignUp requestSignUp = new RequestSignUp();
	// 	String encryptedPassword = passwordEncoder.encode(requestSignUp.getPassword());
	// 	Member member = RequestSignUp.from(requestSignUp, encryptedPassword);
	//
	// 	// when
	// 	memberMapper.save(member);
	//
	// 	// then
	// 	then(memberService).should();
	//
	// }
}