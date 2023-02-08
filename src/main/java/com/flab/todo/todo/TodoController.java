package com.flab.todo.todo;

import java.util.List;

import com.flab.todo.common.dto.SaveTodoRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.todo.common.dto.SaveTodoResponse;
import com.flab.todo.common.dto.TodoListResponse;
import com.flab.todo.common.dto.UpdateTodoRequest;
import com.flab.todo.common.dto.UpdateTodoResponse;
import com.flab.todo.common.dto.UserDetailImpl;

@RestController()
@RequestMapping("/todo")
public class TodoController {

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping()
	public ResponseEntity<List<TodoListResponse>> getTodoList(
		@AuthenticationPrincipal UserDetailImpl userDetail
	) {
		return new ResponseEntity<>(this.todoService.getTodoList(userDetail.getUserId()), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity saveTodo(
		@RequestBody SaveTodoRequest saveTodoRequest,
		@AuthenticationPrincipal UserDetailImpl userDetail
	) {

		System.out.println(saveTodoRequest);
		System.out.println(userDetail);
		SaveTodoResponse response= this.todoService.save(saveTodoRequest, userDetail.getUserId());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity updateTodo(
		@RequestBody UpdateTodoRequest updateTodoRequest,
		@PathVariable("id") Long id,
		@AuthenticationPrincipal UserDetailImpl userDetail
	) {
		UpdateTodoResponse response = this.todoService.update(updateTodoRequest, id, userDetail.getUserId());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteTodo(
		@PathVariable("id") Long id,
		@AuthenticationPrincipal UserDetailImpl userDetail
	) {
		this.todoService.delete(id, userDetail.getUserId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
