package com.flab.todo.todo;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.flab.todo.common.aspect.CheckLoginAspect;
import com.flab.todo.common.dto.TodoListRequest;
import com.flab.todo.common.util.SessionUtil;

@WebMvcTest(TodoController.class)
@Import({AopAutoConfiguration.class, CheckLoginAspect.class})
class TodoControllerTest {
	@MockBean
	private TodoService todoService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("1. Get Todo List Success")
	void getTodoList_success() throws Exception {
		// Given
		MockHttpSession session = new MockHttpSession();
		SessionUtil.setUserId(session, 1L);
		given(todoService.getTodoList(1L)).willReturn(List.of(new TodoListRequest("일찍 일어나기23", false)));

		// When
		var response = mockMvc.perform(get("/todos")
			.session(session)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		// Then
		response.andExpect(status().isOk());
	}

	@Test
	@DisplayName("2. Get Todo List Fail - Required Login")
	void getTodoList_fail_required_login() throws Exception {
		// Given
		// When
		var response = mockMvc.perform(get("/todos")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		// Then
		response.andExpect(status().isUnauthorized());
	}
}
