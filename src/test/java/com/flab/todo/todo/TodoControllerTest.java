package com.flab.todo.todo;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.flab.todo.common.dto.RequestTodoListDto;
import com.flab.todo.common.exception.ExceptionHandlers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerTest {
	@InjectMocks
	TodoController todoController;
	@Mock
	TodoService todoService;
	private MockMvc mockMvc;

	@BeforeEach()
	void settingTest(){
		this.mockMvc = MockMvcBuilders
			.standaloneSetup(todoController)
			.setControllerAdvice(ExceptionHandlers.class)
			.build();
	}

	@Test
	@DisplayName("1. Get Todo List Success")
	void getTodoList_success() throws Exception {
		// Given
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("user", 1L);;
		when(todoService.getTodoList(1L)).thenReturn(List.of(new RequestTodoListDto("일찍 일어나기23", false)));

		// When
		var response = mockMvc.perform(get("/todos")
				.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// Then
		response.andExpect(status().isOk());
	}

	@Test()
	@DisplayName("2. Get Todo List Fail - Because Of Login Fail")
	void getTodoList() throws Exception {
		// Given
		// When
		var response = mockMvc.perform(get("/todos")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		// Then
		response.andExpect(status().isUnauthorized());
	}
}