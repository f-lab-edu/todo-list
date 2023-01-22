package com.flab.todo.todo;

import static com.flab.todo.common.exception.ErrorCode.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.todo.common.dto.RequestTodoListDto;
import com.flab.todo.common.exception.CustomException;

@RestController()
@RequestMapping("/todos")
public class TodoController {

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping()
	public ResponseEntity<List<RequestTodoListDto>> getTodoList(HttpServletRequest request){
		HttpSession session = request.getSession(false);

		if(session == null)
			throw new CustomException(REQUIRED_LOGIN_ERROR);

		Long userId = (Long)session.getAttribute("user");

		return new ResponseEntity<>(this.todoService.getTodoList(userId), HttpStatus.OK);
	}
}
