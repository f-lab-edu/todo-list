package com.flab.todo.todo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flab.todo.common.dto.RequestTodoListDto;

@Service
public class TodoService {

	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public List<RequestTodoListDto> getTodoList(Long userId) {
		return this.todoRepository.findByUserId(userId).stream()
			.map(x -> x.toRequestTodoListDto())
			.collect(Collectors.toList());
	}
}
