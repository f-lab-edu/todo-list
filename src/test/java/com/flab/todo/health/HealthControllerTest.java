package com.flab.todo.health;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthControllerTest {

	@Autowired
	TestRestTemplate testRestTemplate;
	@Test
	void pong() {
		// Given
		ResponseEntity<String> response = null;

		// When
		response = testRestTemplate
			.getForEntity(
				"/ping",
				String.class
			);

		//Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("pong", response.getBody());
	}
}
