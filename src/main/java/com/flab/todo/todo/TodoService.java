package com.flab.todo.todo;

import java.util.List;
import java.util.stream.Collectors;

import com.flab.todo.common.dto.UpdateTodoRequest;
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
		Todo todo = saveTodoRequest.toModel(userId);
		this.todoRepository.save(todo);
	}


	public void update(UpdateTodoRequest updateTodoRequest, Long todoId, Long userId) {
		Todo todo = updateTodoRequest.toModel(userId);
		this.todoRepository.update(todo);
	}


	public void delete(Long todoId, Long userId) {
		this.todoRepository.delete(todoId);
	}
}
