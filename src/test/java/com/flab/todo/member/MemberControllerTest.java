package com.flab.todo.member;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.flab.todo.common.config.mail.JavaMailService;
import com.flab.todo.common.config.mail.MailMessage;
import com.flab.todo.common.config.security.PasswordEncoder;
import com.flab.todo.common.dto.SignUpRequest;
import com.flab.todo.database.entity.Member;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	TestRestTemplate testRestTemplate;

	@MockBean
	private JavaMailService javaMailService;
	// MemberService mocking vs JavaMailService mocking
	@Autowired
	MemberMapper memberMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@BeforeEach
	void beforeEach() {
		memberMapper.deleteAll();
	}

	@Nested
	@DisplayName("회원가입")
	class sendSignUp {
		@Test
		@DisplayName("1. 성공")
		void success() {
			// Given
			SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
				"12345678!q2");
			HttpEntity<?> requestEntity = new HttpEntity<>(signUpRequest);

			// 실제 메일 보내는 로직 mocking
			willDoNothing().given(javaMailService).send(new MailMessage());

			ResponseEntity<Void> response = testRestTemplate
				.exchange(
					"http://localhost:" + randomServerPort + "/sign-up",
					HttpMethod.POST,
					requestEntity,
					Void.class
				);

			assertEquals(HttpStatus.CREATED, response.getStatusCode());
		}

		@Test
		@DisplayName("2. 실패 - 유효하지 않은 이메일")
		void fail_valid_email() {
			// Given
			SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022", "Jaeyeon", "12345678!q2", "12345678!q2");
			HttpEntity<?> requestEntity = new HttpEntity<>(signUpRequest);

			// 실제 메일 보내는 로직 mocking
			willDoNothing().given(javaMailService).send(new MailMessage());

			ResponseEntity<Void> response = testRestTemplate
				.exchange(
					"http://localhost:" + randomServerPort + "/sign-up",
					HttpMethod.POST,
					requestEntity,
					Void.class
				);

			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		}

		@Test
		@DisplayName("3. 실패 - 틀린 비밀번호")
		void fail_valid_password_and_passwordConfirm() {
			// Given
			SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022", "Jaeyeon", "12345678!q2", "12345678!2");
			HttpEntity<?> requestEntity = new HttpEntity<>(signUpRequest);

			// 실제 메일 보내는 로직 mocking
			willDoNothing().given(javaMailService).send(new MailMessage());

			ResponseEntity<Void> response = testRestTemplate
				.exchange(
					"http://localhost:" + randomServerPort + "/sign-up",
					HttpMethod.POST,
					requestEntity,
					Void.class
				);

			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		}
	}

	@Nested
	@DisplayName("이메일 인증")
	class checkEmailToken {

		@Test
		@DisplayName("1. 인증 성공")
		void success() {
			// given
			Member member = SignUpRequest.from(new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
				"12345678!q2"), passwordEncoder.encode("12345678!q2"));
			member.generateToken();
			memberMapper.save(member);
			String token = member.getEmailToken();

			// when
			ResponseEntity<Void> response = testRestTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/check-email-token?token={token}&email={email}",
					Void.class,
					token,
					"cjyeon1022@gmail.com"
				);

			// then
			assertEquals(HttpStatus.OK, response.getStatusCode());
		}

		@Test
		@DisplayName("2. 실패 - 이메일이 없습니다")
		void fail_valid_email() {
			// given
			Member member = SignUpRequest.from(new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
				"12345678!q2"), passwordEncoder.encode("12345678!q2"));
			member.generateToken();
			memberMapper.save(member);
			String token = member.getEmailToken();

			// when
			ResponseEntity<Void> response = testRestTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/check-email-token?token={token}&email={email}",
					Void.class,
					token,
					null
				);

			// then
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		}

		@Test
		@DisplayName("3. 실패 - 토큰이 일치하지 않습니다")
		void fail_valid_token() {
			// given
			Member member = SignUpRequest.from(new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2",
				"12345678!q2"), passwordEncoder.encode("12345678!q2"));
			member.generateToken();
			memberMapper.save(member);
			String token = member.getEmailToken();

			// when
			ResponseEntity<Void> response = testRestTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/check-email-token?token={token}&email={email}",
					Void.class,
					!member.isValidToken(token),
					"cjyeon1022@gmail.com"
				);

			// then
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		}
	}
}
