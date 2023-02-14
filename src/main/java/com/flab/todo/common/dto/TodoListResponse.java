package com.flab.todo.common.dto;

import java.util.Objects;

import com.flab.todo.database.entity.Todo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoListResponse {
	private String value;
	private Boolean isDone;

	public TodoListResponse(String value, Boolean isDone) {
		this.value = value;
		this.isDone = isDone;
	}

	public static TodoListResponse from(Todo todo) {
		return new TodoListResponse(todo.getThings(), todo.getIsDone());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		TodoListResponse that = (TodoListResponse)object;
		return Objects.equals(value, that.value) && Objects.equals(isDone, that.isDone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, isDone);
	}
}
