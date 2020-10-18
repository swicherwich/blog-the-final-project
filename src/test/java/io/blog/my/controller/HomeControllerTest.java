package io.blog.my.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
public class HomeControllerTest extends MockBeans {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void homeGetMappingTest() throws Exception {
		when(postService.findAllOrderedByDatePageable(0)).thenReturn(Page.empty());
		this.mockMvc.perform(get("/home"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("pager"));
	}
}
