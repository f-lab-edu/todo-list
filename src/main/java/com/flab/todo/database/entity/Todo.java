package com.flab.todo.database.entity;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
	private Long id;
	private Long userId;
	private String things;
	private Boolean isDone;

	@Override
	public String toString() {
		return "Todo{" +
			"id=" + id +
			", userId=" + userId +
			", things='" + things + '\'' +
			", isDone=" + isDone +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Todo todo = (Todo)o;
		return Objects.equals(id, todo.id) && Objects.equals(userId, todo.userId)
			&& Objects.equals(things, todo.things) && Objects.equals(isDone, todo.isDone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userId, things, isDone);
	}
}
