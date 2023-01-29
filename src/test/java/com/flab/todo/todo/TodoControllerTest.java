package com.flab.todo.todo;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.flab.todo.common.dto.TodoListRequest;
import com.flab.todo.database.model.Todo;

class TodoControllerTest {

	private TodoService todoService = Mockito.mock(TodoService.class);
	private TodoController todoController = new TodoController(todoService);

	@Test
	@DisplayName("1. Get Todo List Success")
	void getTodoList_success() throws Exception {
		// Given
		List<TodoListRequest> result =
			List.of(
					new Todo(1L, 1L, "아침 일찍 일어나기", false),
					new Todo(1L, 1L, "저녁에 일찍 자기", false)
				).stream()
				.map(TodoListRequest::from)
				.collect(Collectors.toList());
		given(todoService.getTodoList(1L)).willReturn(result);

		// When
		ResponseEntity responseEntity = todoController.getTodoList(1L);

		// Then
		assertThat(responseEntity.getBody(), equalTo(result));
	}
}
