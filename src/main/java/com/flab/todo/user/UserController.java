package com.flab.todo.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< Updated upstream
=======
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

<<<<<<< Updated upstream
=======
	@GetMapping("/sendmail")
	public String sendEmail() {
		return "Email sent successfully";
	}

>>>>>>> Stashed changes
	@PostMapping("/sign-up")
	public ResponseEntity<Integer> signUp(HttpServletRequest request) {
		// TODO : TEST
		return new ResponseEntity<>(200, HttpStatus.CREATED);
	}
}
