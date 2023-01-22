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
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		RequestTodoListDto that = (RequestTodoListDto)o;
		return Objects.equals(value, that.value) && Objects.equals(isDone, that.isDone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, isDone);
	}
}
