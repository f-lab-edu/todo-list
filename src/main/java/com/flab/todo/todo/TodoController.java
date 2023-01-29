package com.flab.todo.todo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.flab.todo.common.annotation.CheckLogin;
import com.flab.todo.common.annotation.CurrentUser;
import com.flab.todo.common.dto.TodoListRequest;

@RestController()
@RequestMapping("/todos")
public class TodoController {

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping()
	@CheckLogin()
	public ResponseEntity<List<TodoListRequest>> getTodoList(@CurrentUser() Long userId) {
		try{
			return new ResponseEntity<>(this.todoService.getTodoList(userId), HttpStatus.OK);
		}catch (NullPointerException exception){
			return new ResponseEntity<>(this.todoService.getTodoList(userId), HttpStatus.OK);
		}catch (IllegalAccessError exception){
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 유저는 권한이 없습니다.");
		}
	}
}
