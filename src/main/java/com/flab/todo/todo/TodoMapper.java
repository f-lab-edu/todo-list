package com.flab.todo.todo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.flab.todo.database.entity.Todo;

@Mapper
public interface TodoMapper {

	List<Todo> findByUserId(Long userId);

	Boolean existById(Long todoId);

	void save(Todo todo);

	void update(Todo todo);

	void delete(@Param("todoId") Long todoId, @Param("userId")Long userId);
}
