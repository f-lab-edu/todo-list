package com.flab.todo.common.dto;

import com.flab.todo.database.entity.Todo;

import lombok.Getter;

@Getter
public class SaveTodoRequest {
	private String things;

	public SaveTodoRequest() {
	}

	public SaveTodoRequest(String things) {
		this.things = things;
	}

	public Todo toModel(Long userId){
		return new Todo(null, userId, this.things, false);
	}

}
