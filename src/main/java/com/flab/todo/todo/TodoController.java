package com.flab.todo.todo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.flab.todo.common.dto.TodoListRequest;

@RestController()
@RequestMapping("/todos")
public class TodoController {

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping()
	public ResponseEntity<List<TodoListRequest>> getTodoList(Long userId) {
		return new ResponseEntity<>(this.todoService.getTodoList(userId), HttpStatus.OK);
	}
}
