package com.flab.todo.todo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flab.todo.common.dto.TodoListRequest;

@Service
public class TodoService {

	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public List<TodoListRequest> getTodoList(Long userId) {
		return this.todoRepository.findByUserId(userId).stream()
			.map(TodoListRequest::from)
			.collect(Collectors.toList());
	}
}
