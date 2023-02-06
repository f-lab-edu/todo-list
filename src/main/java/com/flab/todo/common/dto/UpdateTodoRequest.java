package com.flab.todo.common.dto;

import com.flab.todo.database.entity.Todo;

import lombok.Getter;

@Getter
public class UpdateTodoRequest {
	private String things;
	private Boolean isDone;

	public UpdateTodoRequest() {
	}

	public Todo toModel(Long userId){
		return new Todo(null, userId, this.things, !this.isDone);
	}

}
