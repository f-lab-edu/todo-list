package com.flab.todo.todo;

import java.util.List;
import java.util.stream.Collectors;

import com.flab.todo.database.entity.Todo;

import org.springframework.stereotype.Service;

import com.flab.todo.common.dto.TodoListResponse;
import com.flab.todo.common.dto.SaveTodoRequest;

@Service
public class TodoService {

	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public List<TodoListResponse> getTodoList(Long userId) {
		return this.todoRepository.findByUserId(userId).stream()
			.map(TodoListResponse::from)
			.collect(Collectors.toList());
	}

	public void save(SaveTodoRequest saveTodoRequest, Long userId) {

		System.out.println(saveTodoRequest);
		Todo todo = saveTodoRequest.toModel(userId);
		System.out.println(todo);
		this.todoRepository.save(todo);
	}
}
