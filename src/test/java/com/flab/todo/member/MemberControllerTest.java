package com.flab.todo.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MemberMapper memberMapper;

	// TODO ing~
	// @Test
	// @DisplayName()
	// void case1() throws Exception {
	//     // given
	//
	//     // when
	//
	//     // then
	// }

}