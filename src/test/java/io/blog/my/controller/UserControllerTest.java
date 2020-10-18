package io.blog.my.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest extends MockBeans {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void userSignUpGetMappingTest() throws Exception {
		mockMvc.perform(get("/sign-up"))
				.andExpect(model().attributeExists("user"))
				.andExpect(status().isOk());
	}
}
