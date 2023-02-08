package com.flab.todo.todo;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.flab.todo.common.dto.SaveTodoRequest;
import com.flab.todo.common.dto.TodoListResponse;
import com.flab.todo.common.dto.UpdateTodoRequest;
import com.flab.todo.database.entity.Todo;

class TodoServiceTest {

	private TodoMapper todoMapper = Mockito.mock(TodoMapper.class);
	private TodoService todoService = new TodoService(todoMapper);

	@Nested
	@DisplayName("Get Todo List")
	class GetTodoListTest {
		@Test
		@DisplayName("Todo List를 성공적으로 반환")
		public void case1() {
			// Given
			List<Todo> result = List.of(new Todo(1L, 1L, "아침 일찍 일어나기", false), new Todo(1L, 1L, "저녁에 일찍 자기", false));
			given(todoMapper.findByUserId(1L)).willReturn(result);

			// When
			List<TodoListResponse> requestTodoListDtoList = todoService.getTodoList(1L);

			// Then
			assertThat(requestTodoListDtoList,
				equalTo(result.stream().map(TodoListResponse::from).collect(Collectors.toList())));
		}

		@Test
		@DisplayName("없는 경우 빈 배열 반환")
		public void case2() {
			// Given
			given(todoMapper.findByUserId(1L)).willReturn(new ArrayList<>());

			// When
			List<TodoListResponse> requestTodoListDtoList = todoService.getTodoList(1L);

			// Then
			assertThat(requestTodoListDtoList,
				equalTo(new ArrayList<>()));
		}
	}

	@Nested
	@DisplayName("Save Todo")
	class SaveTodoTest {
		@Test
		@DisplayName("Todo를 성공적으로 생성")
		public void case1() {
			// Given
			SaveTodoRequest saveTodoRequest = new SaveTodoRequest("아침 일찍 일어나기");
			given(todoMapper.existById(1L)).willReturn(false);

			// When
			todoService.save(saveTodoRequest, 1L);

			// Then
			verify(todoMapper, times(1)).save(new Todo(null, 1L, "아침 일찍 일어나기", false));
		}
	}

	@Nested
	@DisplayName("Update Todo")
	class UpdateTodoTest {
		@Test
		@DisplayName("Todo를 성공적으로 업데이트")
		public void case1() {
			// Given
			UpdateTodoRequest updateTodoRequest = new UpdateTodoRequest("아침 일찍 일어나기", true);
			given(todoMapper.existById(1L)).willReturn(true);

			// When
			todoService.update(updateTodoRequest, 1L, 1L);

			// Then
			verify(todoMapper, times(1)).update(updateTodoRequest.toModel(1L, 1L));
		}

		@Test
		@DisplayName("존재하지 않는 Todo라면 새로 생성하기")
		public void case2() {
			// Given
			UpdateTodoRequest updateTodoRequest = new UpdateTodoRequest("아침 일찍 일어나기", true);

			// When
			todoService.update(updateTodoRequest, 1L, 1L);

			// Then
			verify(todoMapper, times(1)).save(new Todo(1L, 1L, "아침 일찍 일어나기", true));
		}
	}

	@Nested
	@DisplayName("Delete Todo")
	class DeleteTodoTest {
		@Test
		@DisplayName("Todo를 성공적으로 삭제")
		public void case1() {
			// Given
			Long todoId = 1L;
			Long userId = 1L;

			// When
			todoService.delete(todoId, userId);

			// Then
			verify(todoMapper, times(1)).delete(todoId, userId);
		}
	}
}
