package com.flab.todo.member;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.flab.todo.common.dto.SignUpRequest;
import com.flab.todo.database.entity.Member;

class MemberServiceTest {
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private MemberMapper memberMapper = Mockito.mock(MemberMapper.class);
	private ApplicationEventPublisher eventPublisher = Mockito.mock(ApplicationEventPublisher.class);
	private MemberService memberService = new MemberService(passwordEncoder, memberMapper, eventPublisher);

	@Nested
	@DisplayName("회원가입")
	class SendSignUpTest {
		@Test
		@DisplayName("1. 성공 - 이메일 전송")
		public void case1() {
			// Given
			SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
				"12345678!q2");
			given(memberMapper.existsByEmail("cjyeon1022@gmail.com")).willReturn(false);

			// When
			memberService.requestVerificationToken(signUpRequest);

			// Then
			verify(memberMapper, times(1)).save(ArgumentMatchers.any(Member.class));
		}

		@Test
		@DisplayName("2. 실패 - 이미 존재하는 Email")
		public void case2() {
			// Given
			SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
				"12345678!q2");
			given(memberMapper.existsByEmail("cjyeon1022@gmail.com")).willReturn(true);

			// When
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				memberService.requestVerificationToken(signUpRequest);
			});

			// Then
			assertTrue(exception.getMessage().equals("이미 가입된 회원입니다."));
		}

		@Test
		@DisplayName("3. 실패 - 일치하지 않는 패스워드")
		public void case3() {
			// Given
			SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
				"12345678!112");
			given(memberMapper.existsByEmail("cjyeon1022@gmail.com")).willReturn(false);

			// When
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				memberService.requestVerificationToken(signUpRequest);
			});

			// Then
			assertTrue(exception.getMessage().equals("비밀번호가 서로 일치하지 않습니다."));
		}
	}

	@Nested
	@DisplayName("이메일 검증")
	class CheckEmailTokenTest {

		@Test
		@DisplayName("1. 성공")
		void cas1() throws Exception {
			// given
			Member member = SignUpRequest.from(
				new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2", "12345678!q2"),
				passwordEncoder.encode("12345678!q2"));
			member.generateToken();
			memberMapper.save(member);
			String token = member.getEmailToken();
			given(memberMapper.findByEmailAndEmailToken(member.getEmail(), token)).willReturn(Optional.of(member));

			// when
			memberService.completeSignUp(token, member.getEmail());

			// then
			verify(memberMapper, times(1)).update(ArgumentMatchers.any(Member.class));
		}

		@Test
		@DisplayName("2. 실패 - 존재하지 않는 이메일")
		void case2() {
			// given
			Member member = SignUpRequest.from(new SignUpRequest(null, "Jaeyeon", "12345678!q2", "12345678!q2"),
				passwordEncoder.encode("12345678!q2"));
			member.generateToken();
			memberMapper.save(member);
			String token = member.getEmailToken();
			given(memberMapper.findByEmailAndEmailToken(member.getEmail(), token)).willReturn(Optional.empty());

			// When
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				memberService.completeSignUp(token, member.getEmail());
			});

			// Then
			assertTrue(exception.getMessage().equals("Email not found"));
		}

		@Test
		@DisplayName("3. 실패 - 틀린 토큰")
		void case3() {
			// given
			Member member = SignUpRequest.from(
				new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2", "12345678!q2"),
				passwordEncoder.encode("12345678!q2"));
			member.generateToken();
			memberMapper.save(member);
			String randomToken = UUID.randomUUID().toString();
			given(memberMapper.findByEmailAndEmailToken(member.getEmail(), randomToken)).willReturn(
				Optional.of(member));

			// When
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				memberService.completeSignUp(randomToken, member.getEmail());
			});

			// Then
			assertEquals("Wrong Token", exception.getMessage());
		}
	}
}
