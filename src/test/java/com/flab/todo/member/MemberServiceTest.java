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
import org.springframework.security.core.userdetails.UserDetails;

import com.flab.todo.common.config.mail.JavaMailService;
import com.flab.todo.common.config.security.PasswordEncoder;
import com.flab.todo.common.dto.SignUpRequest;
import com.flab.todo.common.exception.custom.UnAuthorizedException;
import com.flab.todo.database.entity.Member;

class MemberServiceTest {
	private PasswordEncoder passwordEncoder = new PasswordEncoder();
	private MemberMapper memberMapper = Mockito.mock(MemberMapper.class);
	private JavaMailService javaMailService = Mockito.mock(JavaMailService.class);
	private MemberService memberService = new MemberService(passwordEncoder, memberMapper, javaMailService);

	@Nested
	@DisplayName("회원가입")
	class sendSignUp {
		@Test
		@DisplayName("1. 성공 - 이메일 전송")
		public void case1() {
			// Given
			SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
				"12345678!q2");
			given(memberMapper.existsByEmail("cjyeon1022@gmail.com")).willReturn(false);

			// When
			memberService.sendSignUpEmail(signUpRequest);

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
				memberService.sendSignUpEmail(signUpRequest);
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
				memberService.sendSignUpEmail(signUpRequest);
			});

			// Then
			assertTrue(exception.getMessage().equals("비밀번호가 서로 일치하지 않습니다."));
		}
	}

	@Nested
	@DisplayName("이메일 검증")
	class checkEmailToken {

		@Test
		@DisplayName("1. 성공")
		void cas1() throws Exception {
			// given
			Member member = SignUpRequest.from(new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
				"12345678!q2"), passwordEncoder.encode("12345678!q2"));
			member.generateToken();
			memberMapper.save(member);
			String token = member.getEmailToken();
			given(memberMapper.findByEmailAndEmailToken(member.getEmail(), token)).willReturn(member);

			// when
			memberService.verifyEmailAndComplete(token, member.getEmail());

			// then
			verify(memberMapper, times(1)).update(ArgumentMatchers.any(Member.class));
		}

		@Test
		@DisplayName("2. 실패 - 존재하지 않는 이메일")
		void case2() {
			// given
			Member member = SignUpRequest.from(new SignUpRequest(null, "Jaeyeon", "12345678!q2",
				"12345678!q2"), passwordEncoder.encode("12345678!q2"));
			member.generateToken();
			memberMapper.save(member);
			String token = member.getEmailToken();
			given(memberMapper.findByEmailAndEmailToken(member.getEmail(), token)).willReturn(null);

			// When
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				memberService.verifyEmailAndComplete(token, member.getEmail());
			});

			// Then
			assertTrue(exception.getMessage().equals("Email not found"));
		}

		// TODO ERROR
		@Test
		@DisplayName("3. 실패 - 틀린 토큰")
		void case3() {
			// given
			Member member = SignUpRequest.from(new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
				"12345678!q2"), passwordEncoder.encode("12345678!q2"));
			member.generateToken();
			memberMapper.save(member);
			String randomToken = UUID.randomUUID().toString();
			given(memberMapper.findByEmailAndEmailToken(member.getEmail(), randomToken)).willReturn(member);

			// When
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				memberService.verifyEmailAndComplete(randomToken, member.getEmail());
			});

			// Then
			assertEquals("Wrong Token", exception.getMessage());
		}
	}

	@Nested
	@DisplayName("회원 로그인")
	class login {
		@Test
		@DisplayName("1. 성공 - 회원 로그인")
		void case3() throws Exception {
			// given
			Member member = SignUpRequest.from(new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
				"12345678!q2"), passwordEncoder.encode("12345678!q2"));
			member.generateToken();
			memberMapper.save(member);
			given(memberMapper.findByEmail(member.getEmail())).willReturn(Optional.of(member));

			// when
			UserDetails userDetails = memberService.loadUserByUsername(member.getEmail());

			// then
			assertEquals(member.getEmail(), userDetails.getUsername());
		}
	}

	@Test
	@DisplayName("2. 실패 - 회원 정보 불일치(이메일)")
	void case4() throws Exception {
		// given
		Member member = SignUpRequest.from(new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
			"12345678!q2"), passwordEncoder.encode("12345678!q2"));
		member.generateToken();
		memberMapper.save(member);
		given(memberMapper.findByEmail(member.getEmail())).willReturn(Optional.empty());

		// When
		Exception exception = assertThrows(UnAuthorizedException.class, () -> {
			memberService.loadUserByUsername(member.getEmail());
		});

		// Then
		assertTrue(exception.getMessage().equals("Unauthorized Request"));
	}
}
