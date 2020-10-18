package io.blog.my.controller;

import io.blog.my.service.CommentService;
import io.blog.my.service.PostService;
import io.blog.my.service.UserService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;

public class MockBeans {
	
	@MockBean
	protected PostService postService;
	
	@MockBean
	protected AccessDeniedHandler accessDeniedHandler;
	
	@MockBean
	protected DataSource dataSource;
	
	@MockBean
	protected BCryptPasswordEncoder cryptPasswordEncoder;
	
	@MockBean
	protected UserService userService;
	
	@MockBean
	protected CommentService commentService;
	
}
