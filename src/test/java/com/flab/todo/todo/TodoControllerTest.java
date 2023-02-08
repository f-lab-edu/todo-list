package com.flab.todo.todo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.flab.todo.common.config.security.PasswordEncoder;
import com.flab.todo.common.dto.SaveTodoRequest;
import com.flab.todo.common.dto.SaveTodoResponse;
import com.flab.todo.common.dto.TodoListResponse;
import com.flab.todo.common.dto.UpdateTodoRequest;
import com.flab.todo.common.dto.UpdateTodoResponse;
import com.flab.todo.database.entity.Member;
import com.flab.todo.database.entity.Todo;
import com.flab.todo.member.MemberMapper;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerTest {
	@LocalServerPort
	int randomServerPort;

	@Autowired
	TestRestTemplate testRestTemplate;

	@BeforeAll
	private static void setDB(
		@Autowired MemberMapper memberMapper,
		@Autowired TodoMapper todoMapper,
		@Autowired PasswordEncoder passwordEncoder
	){
		memberMapper.save(
			new Member(1L, "seonjin.kim@naver.com", "seonjin", passwordEncoder.encode("emm05235"),
			null, null, null, null, true)
		);
		memberMapper.update(
			new Member(1L, "seonjin.kim@naver.com", "seonjin", passwordEncoder.encode("emm05235"),
				null, null, null, null, true)
		);
		todoMapper.save(
			new Todo(1L, 1L, "일찍 자고 일어나기", false)
		);
	}

	@Nested
	@DisplayName("Todo list 가져오기")
	class GetTodoList {
		@Test
		@DisplayName("1. 성공")
		public void success(){
			//given
			testRestTemplate = new TestRestTemplate("seonjin.kim@naver.com","emm05235");

			//when
			ResponseEntity<List<TodoListResponse>> response = testRestTemplate
				.exchange(
					"http://localhost:"+randomServerPort+"/todo",
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<TodoListResponse>>() {}
				);

			//then
			assertEquals(HttpStatus.OK, response.getStatusCode());
		}

		@Test
		@DisplayName("2. 로그인 실패 시 UNAUTHORIZED 반환")
		public void fail_login_fail(){
			//given
			testRestTemplate = new TestRestTemplate("seonjin.kim2@naver.com","emm05235");

			//when
			ResponseEntity<Object> response = testRestTemplate
				.exchange(
					"http://localhost:"+randomServerPort+"/todo",
					HttpMethod.GET,
					null,
					Object.class
				);

			//then
			assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		}
	}

	@Nested
	@DisplayName("Todo 생성하기")
	class SaveTodo {
		@Test
		@DisplayName("1. 성공")
		public void success(){
			//given
			testRestTemplate = new TestRestTemplate("seonjin.kim@naver.com","emm05235");
			SaveTodoRequest saveTodoRequest = new SaveTodoRequest("일찍 일어나기");
			HttpEntity<?> requestEntity = new HttpEntity<>(saveTodoRequest);

			//when
			ResponseEntity<SaveTodoResponse> response = testRestTemplate
				.exchange(
					"http://localhost:"+randomServerPort+"/todo",
					HttpMethod.POST,
					requestEntity,
					SaveTodoResponse.class
				);

			//then
			assertEquals(HttpStatus.CREATED, response.getStatusCode());
		}

		@Test
		@DisplayName("2. 로그인 실패 시 UNAUTHORIZED 반환")
		public void fail_login_fail(){
			//given
			testRestTemplate = new TestRestTemplate("seonjin.kim2@naver.com","emm05235");
			SaveTodoRequest saveTodoRequest = new SaveTodoRequest("일찍 일어나기");
			HttpEntity<?> requestEntity = new HttpEntity<>(saveTodoRequest);

			//when
			ResponseEntity<SaveTodoResponse> response = testRestTemplate
				.exchange(
					"http://localhost:"+randomServerPort+"/todo",
					HttpMethod.POST,
					requestEntity,
					SaveTodoResponse.class
				);

			//then
			assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		}
	}

	@Nested
	@DisplayName("Todo 업데이트하기")
	class UpdateTodo {
		@Test
		@DisplayName("1. 성공")
		public void success(){
			//given
			testRestTemplate = new TestRestTemplate("seonjin.kim@naver.com","emm05235");
			UpdateTodoRequest updateTodoRequest = new UpdateTodoRequest("늦게 일어나기", false);
			HttpEntity<?> requestEntity = new HttpEntity<>(updateTodoRequest);

			//when
			ResponseEntity<UpdateTodoResponse> response = testRestTemplate
				.exchange(
					"http://localhost:"+randomServerPort+"/todo/"+1,
					HttpMethod.PUT,
					requestEntity,
					UpdateTodoResponse.class
				);

			//then
			assertEquals(HttpStatus.OK, response.getStatusCode());
		}

		@Test
		@DisplayName("2. 성공 - 기존에 저장된 todo가 아니라 새로운 todo가 들어오면 새로 생성하기")
		public void success_with_new_create_todo(){
			//given
			testRestTemplate = new TestRestTemplate("seonjin.kim@naver.com","emm05235");
			Long notSavedTodoId = 10L;
			UpdateTodoRequest updateTodoRequest = new UpdateTodoRequest("늦게 일어나기", false);
			HttpEntity<?> requestEntity = new HttpEntity<>(updateTodoRequest);

			//when
			ResponseEntity<UpdateTodoResponse> response = testRestTemplate
				.exchange(
					"http://localhost:"+randomServerPort+"/todo/"+notSavedTodoId,
					HttpMethod.PUT,
					requestEntity,
					UpdateTodoResponse.class
				);

			//then
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals("늦게 일어나기", response.getBody().getThings());
			assertEquals(false, response.getBody().getIsDone());
		}

		@Test
		@DisplayName("3. 로그인 실패 시 UNAUTHORIZED 반환")
		public void fail_login_fail(){
			//given
			testRestTemplate = new TestRestTemplate("seonjin.kim2@naver.com","emm05235");
			UpdateTodoRequest updateTodoRequest = new UpdateTodoRequest("늦게 일어나기", false);
			HttpEntity<?> requestEntity = new HttpEntity<>(updateTodoRequest);

			//when
			ResponseEntity<UpdateTodoResponse> response = testRestTemplate
				.exchange(
					"http://localhost:"+randomServerPort+"/todo/"+1,
					HttpMethod.PUT,
					requestEntity,
					UpdateTodoResponse.class
				);

			//then
			assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		}
	}

	@Nested
	@DisplayName("Todo 삭제하기")
	class DeleteTodo {
		@Test
		@DisplayName("1. 성공")
		public void success(){
			//given
			testRestTemplate = new TestRestTemplate("seonjin.kim@naver.com","emm05235");

			//when
			ResponseEntity<UpdateTodoResponse> response = testRestTemplate
				.exchange(
					"http://localhost:"+randomServerPort+"/todo/"+1,
					HttpMethod.DELETE,
					null,
					UpdateTodoResponse.class
				);

			//then
			assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		}

		@Test
		@DisplayName("2. 로그인 실패 시 UNAUTHORIZED 반환")
		public void fail_login_fail(){
			//given
			testRestTemplate = new TestRestTemplate("seonjin.kim2@naver.com","emm05235");

			//when
			ResponseEntity<UpdateTodoResponse> response = testRestTemplate
				.exchange(
					"http://localhost:"+randomServerPort+"/todo/"+1,
					HttpMethod.DELETE,
					null,
					UpdateTodoResponse.class
				);

			//then
			assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		}
	}
}