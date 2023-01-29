package com.flab.todo.member;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

	@InjectMocks
	UserController userController;
	@Mock
	UserService userService;
	private MockMvc mockMvc;

	@Test
	void test_1() throws Exception {
		// given

		// when

		// then
	}
}