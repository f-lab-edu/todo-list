package com.flab.todo.todo;

import java.util.List;

import com.flab.todo.database.entity.Todo;

public interface TodoRepository {
	List<Todo> findByUserId(Long userId);

	Todo save(Todo todo);

	Todo update(Todo todo);

	Todo delete(Long todoId);
}
