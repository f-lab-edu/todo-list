package com.flab.todo.common.dto;

import com.flab.todo.database.entity.Todo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor()
public class UpdateTodoRequest {
	private String things;
	private Boolean isDone;

	public UpdateTodoRequest(String things, Boolean isDone) {
		this.things = things;
		this.isDone = isDone;
	}

	public Todo toModel(Long todoId, Long userId) {
		return new Todo(todoId, userId, this.things, this.isDone);
	}

}
