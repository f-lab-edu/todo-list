package com.flab.todo.todo;

import java.util.List;

import com.flab.todo.common.dto.SaveTodoRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.todo.common.dto.TodoListResponse;

@RestController()
@RequestMapping("/todo")
public class TodoController {

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping()
	public ResponseEntity<List<TodoListResponse>> getTodoList(Long userId) {
		userId = 1L;
		return new ResponseEntity<>(this.todoService.getTodoList(userId), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity saveTodo(@RequestBody()SaveTodoRequest saveTodoRequest, Long userId) {
		userId = 1L;
		this.todoService.save(saveTodoRequest, userId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
