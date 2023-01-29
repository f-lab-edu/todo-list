package com.flab.todo.common.dto;

import java.util.Objects;

import com.flab.todo.database.entity.Todo;

import lombok.Getter;

@Getter
public class TodoListRequest {
	private String value;
	private Boolean isDone;

	public TodoListRequest(String value, Boolean isDone) {
		this.value = value;
		this.isDone = isDone;
	}

	public static TodoListRequest from(Todo todo) {
		return new TodoListRequest(todo.getThings(), todo.getIsDone());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		TodoListRequest that = (TodoListRequest)object;
		return Objects.equals(value, that.value) && Objects.equals(isDone, that.isDone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, isDone);
	}
}
