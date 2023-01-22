package com.flab.todo.todo;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.todo.common.dto.RequestTodoListDto;
import com.flab.todo.database.model.Todo;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
	@InjectMocks
	private TodoService todoService;

	@Mock
	private TodoRepository todoRepository;

	@Test
	@DisplayName("1. Todo List를 성공적으로 반환")
	void getTodoList() {
		// Given
		List<Todo> result = List.of( new Todo(1L, "아침 일찍 일어나기", false), new Todo(1L, "저녁에 일찍 자기", false) );
		when(todoRepository.findByUserId(1L)).thenReturn(result);

		// When
		List<RequestTodoListDto> requestTodoListDtoList = todoService.getTodoList(1L);

		// Then
		assertThat(requestTodoListDtoList, equalTo(result.stream().map(x -> x.toRequestTodoListDto()).collect(Collectors.toList())));
	}
}