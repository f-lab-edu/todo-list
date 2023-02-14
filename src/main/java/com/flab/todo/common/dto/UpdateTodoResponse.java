package com.flab.todo.common.dto;

import com.flab.todo.database.entity.Todo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateTodoResponse {
	private Long id;
	private String things;
	private Boolean isDone;

	public UpdateTodoResponse(Long id, String things, Boolean isDone) {
		this.id = id;
		this.things = things;
		this.isDone = isDone;
	}

	public static UpdateTodoResponse from(Todo todo) {
		return new UpdateTodoResponse(todo.getId(), todo.getThings(), todo.getIsDone());
	}
}
