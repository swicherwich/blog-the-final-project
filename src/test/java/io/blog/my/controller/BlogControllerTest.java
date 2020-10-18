package io.blog.my.controller;

import io.blog.my.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BlogController.class)
public class BlogControllerTest extends MockBeans {
	
	@Autowired
	MockMvc mockMvc;
	
	

	@Test
	public void blogGetMappingTest() throws Exception {
		when(userService.findByUsername("username")).thenReturn(Optional.empty());
		mockMvc.perform(get("/blog/username"))
				.andExpect(view().name("/error"));
	}
	
	@Test
	public void blogGetMappingOptionalExistsTest() throws Exception {
		User user = new User();
		user.setUsername("username");
		
		when(userService.findByUsername("username")).thenReturn(Optional.of(user));
		when(postService.findByUserOrderedByDatePageable(user, 0)).thenReturn(Page.empty());
		
		mockMvc.perform(get("/blog/username"))
				.andExpect(model().attributeExists("pager"))
				.andExpect(model().attributeExists("user"));
		
	}
}
