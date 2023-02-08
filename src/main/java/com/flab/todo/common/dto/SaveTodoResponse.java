package com.flab.todo.common.dto;

import com.flab.todo.database.entity.Todo;

import lombok.Getter;

@Getter
public class SaveTodoResponse {
	private Long id;
	private String things;
	private Boolean isDone;

	public SaveTodoResponse() {
	}

	public SaveTodoResponse(Long id, String things, Boolean isDone) {
		this.id = id;
		this.things = things;
		this.isDone = isDone;
	}

	public static SaveTodoResponse from(Todo todo){
		return new SaveTodoResponse(todo.getId(), todo.getThings(), todo.getIsDone());
	}

	@Override
	public String toString() {
		return "SaveTodoResponse{" +
			"id=" + id +
			", things='" + things + '\'' +
			", isDone=" + isDone +
			'}';
	}
}
