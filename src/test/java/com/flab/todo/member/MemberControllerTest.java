package com.flab.todo.member;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

	@Mock
	private JavaMailSender javaMailSender;

	@InjectMocks
	private MemberController memberController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
	}

	// @Test
	// void sendSignUpEmail_ShouldCallServiceAndReturnCreated() throws Exception {
	// 	// given
	// 	SignUpRequest signUpRequest = new SignUpRequest("cjyeon1022@gmail.com", "Jaeyeon", "12345678!q2", "12345678!q2");
	//
	// 	// when
	// 	mockMvc.perform(post("/sign-up")
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 		.andExpect(status().isCreated());
	//
	// 	// then
	// 	verify(memberService).sendSignUpEmail(any(SignUpRequest.class));
	// }

}