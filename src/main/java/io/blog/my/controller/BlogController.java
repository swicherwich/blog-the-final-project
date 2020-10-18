package io.blog.my.controller;

import io.blog.my.model.Post;
import io.blog.my.model.User;
import io.blog.my.service.PostService;
import io.blog.my.service.UserService;
import io.blog.my.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class BlogController {
	
	private final UserService userService;
	private final PostService postService;
	
	@Autowired
	public BlogController(UserService userService, PostService postService) {
		this.userService = userService;
		this.postService = postService;
	}
	
	@GetMapping("/blog/{username}")
	public String blogForUsername(@PathVariable String username,
	                              @RequestParam(defaultValue = "0") int page,
	                              Model model) {
		
		Optional<User> optionalUser = userService.findByUsername(username);
		
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Page<Post> posts = postService.findByUserOrderedByDatePageable(user, page);
			
			model.addAttribute("pager", new Pager(posts));
			model.addAttribute("user", user);
			return "/blog";
		}
		return "/error";
	}
}
