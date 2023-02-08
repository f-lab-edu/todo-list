package com.flab.todo.todo;

import java.util.List;
import java.util.stream.Collectors;

import com.flab.todo.common.dto.SaveTodoResponse;
import com.flab.todo.common.dto.UpdateTodoRequest;
import com.flab.todo.common.dto.UpdateTodoResponse;
import com.flab.todo.database.entity.Todo;

import org.springframework.stereotype.Service;

import com.flab.todo.common.dto.TodoListResponse;
import com.flab.todo.common.dto.SaveTodoRequest;

@Service
public class TodoService {

	private final TodoMapper todoMapper;

	public TodoService(TodoMapper todoRepository) {
		this.todoMapper = todoRepository;
	}

	public List<TodoListResponse> getTodoList(Long userId) {
		return this.todoMapper.findByUserId(userId).stream()
			.map(TodoListResponse::from)
			.collect(Collectors.toList());
	}

	public SaveTodoResponse save(SaveTodoRequest saveTodoRequest, Long userId) {
		Todo todo = saveTodoRequest.toModel(userId);
		this.todoMapper.save(todo);
		return SaveTodoResponse.from(todo);
	}


	public UpdateTodoResponse update(UpdateTodoRequest updateTodoRequest, Long todoId, Long userId) {
		Todo todo = updateTodoRequest.toModel(todoId, userId);

		if (this.todoMapper.existById(todoId)) {
			this.todoMapper.update(todo);
		} else {
			this.todoMapper.save(todo);
		}

		return UpdateTodoResponse.from(todo);
	}

	public void delete(Long todoId, Long userId) {
		this.todoMapper.delete(todoId, userId);
	}
}
