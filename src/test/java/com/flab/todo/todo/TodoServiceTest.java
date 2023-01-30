package com.flab.todo.todo;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.flab.todo.common.dto.TodoListRequest;
import com.flab.todo.database.entity.Todo;

class TodoServiceTest {

	private TodoRepository todoRepository = Mockito.mock(TodoRepository.class);
	private TodoService todoService = new TodoService(todoRepository);

	@Test
	@DisplayName("1. Todo List를 성공적으로 반환")
	void getTodoList() {
		// Given
		List<Todo> result = List.of(new Todo(1L, 1L, "아침 일찍 일어나기", false), new Todo(1L, 1L, "저녁에 일찍 자기", false));
		given(todoRepository.findByUserId(1L)).willReturn(result);

		// When
		List<TodoListRequest> requestTodoListDtoList = todoService.getTodoList(1L);

		// Then
		assertThat(requestTodoListDtoList,
			equalTo(result.stream().map(TodoListRequest::from).collect(Collectors.toList())));
	}
}
