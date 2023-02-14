package com.flab.todo.common.dto;

import com.flab.todo.database.entity.Todo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor()
public class SaveTodoRequest {
	private String things;

	public SaveTodoRequest(String things) {
		this.things = things;
	}

	public Todo toModel(Long userId) {
		return new Todo(null, userId, this.things, false);
	}
}
