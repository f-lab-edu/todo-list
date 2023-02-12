package com.flab.todo.member;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.flab.todo.common.config.mail.JavaMailService;
import com.flab.todo.common.config.mail.MailMessage;
import com.flab.todo.common.dto.SaveTodoResponse;
import com.flab.todo.common.dto.SignUpRequest;
import com.flab.todo.common.dto.TodoListResponse;
import com.flab.todo.common.dto.UpdateTodoResponse;
import com.flab.todo.todo.TodoMapper;

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

	@Nested
	@DisplayName("Todo list 가져오기")
	class GetTodoList {
		@Test
		@DisplayName("1. 성공")
		public void success() {
			// Given
			SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2", "12345678!q2");
			HttpEntity<?> requestEntity = new HttpEntity<>(signUpRequest);

			// 실제 메일 보내는 로직 mocking
			willDoNothing().given(javaMailService).send(new MailMessage());

			ResponseEntity<Object> response = testRestTemplate
				.exchange(
					"http://localhost:"+randomServerPort+"/sign-up",
					HttpMethod.POST,
					requestEntity,
					Object.class
				);

			assertEquals(HttpStatus.CREATED, response.getStatusCode());
		}

		@Test
		@DisplayName("2. 실패 - 유효하지 않은 이메일")
		public void fail_valid_email() {
			// Given
			SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022", "Jaeyeon", "12345678!q2", "12345678!q2");
			HttpEntity<?> requestEntity = new HttpEntity<>(signUpRequest);

			// 실제 메일 보내는 로직 mocking
			willDoNothing().given(javaMailService).send(new MailMessage());

			ResponseEntity<Object> response = testRestTemplate
				.exchange(
					"http://localhost:"+randomServerPort+"/sign-up",
					HttpMethod.POST,
					requestEntity,
					Object.class
				);

			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		}
	}
}