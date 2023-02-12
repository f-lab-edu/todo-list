package com.flab.todo.member;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.flab.todo.common.config.mail.JavaMailService;
import com.flab.todo.common.config.security.PasswordEncoder;
import com.flab.todo.common.dto.SignUpRequest;
import com.flab.todo.common.dto.TodoListResponse;
import com.flab.todo.database.entity.Member;
import com.flab.todo.database.entity.Todo;

class MemberServiceTest {
	private PasswordEncoder passwordEncoder = new PasswordEncoder();
	private MemberMapper memberMapper = Mockito.mock(MemberMapper.class);
	private JavaMailService javaMailService = Mockito.mock(JavaMailService.class);
	private MemberService memberService = new MemberService(passwordEncoder, memberMapper, javaMailService);

	@Nested
	@DisplayName("Get Todo List")
	class GetTodoListTest {
		@Test
		@DisplayName("Todo List를 성공적으로 반환")
		public void case1() {
			// Given
			SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2", "12345678!q2");
			given(memberMapper.existsByEmail("cjyeon1022@gmail.com")).willReturn(false);

			// When
			memberService.sendSignUpEmail(signUpRequest);

			// Then
			verify(memberMapper, times(1)).save(ArgumentMatchers.any(Member.class));
		}

		@Test
		@DisplayName("이미 존재하는 Email")
		public void case2() {
			// Given
			SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2", "12345678!q2");
			given(memberMapper.existsByEmail("cjyeon1022@gmail.com")).willReturn(true);

			// When
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				memberService.sendSignUpEmail(signUpRequest);
			});

			// Then
			assertTrue(exception.getMessage().equals("이미 가입된 회원입니다."));
		}
	}

	/*

		validateDuplicateUser(signUpRequest);
		validatePasswordUser(signUpRequest);

		String encryptedPassword = passwordEncoder.encode(signUpRequest.getPassword());
		Member member = SignUpRequest.from(signUpRequest, encryptedPassword);
		member.generateToken();

		sendVerificationEmailWithToken(member);

		memberMapper.save(member);
	 */
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