package com.flab.todo.database.model;

import com.flab.todo.common.dto.RequestTodoListDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Todo {
	private Long id;
	private Long userId;
	private String things;
	private Boolean isDone;

	public Todo(Long userId, String things, Boolean isDone) {
		this.userId = userId;
		this.things = things;
		this.isDone = isDone;
	}

	public RequestTodoListDto toRequestTodoListDto(){
		return new RequestTodoListDto(this.things, this.isDone);
	}
}
