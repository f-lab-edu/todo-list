package com.flab.todo.common.dto;

import java.util.Objects;

import lombok.Getter;

@Getter
public class RequestTodoListDto {
	private String value;
	private Boolean isDone;

	public RequestTodoListDto(String value, Boolean isDone) {
		this.value = value;
		this.isDone = isDone;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		RequestTodoListDto that = (RequestTodoListDto)object;
		return Objects.equals(value, that.value) && Objects.equals(isDone, that.isDone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, isDone);
	}
}
