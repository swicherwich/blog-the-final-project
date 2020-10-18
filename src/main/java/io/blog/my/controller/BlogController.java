package io.blog.my.controller;

import io.blog.my.model.Post;
import io.blog.my.model.User;
import io.blog.my.service.PostService;
import io.blog.my.service.UserService;
import io.blog.my.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView blogForUsername(@PathVariable String username,
	                                    @RequestParam(defaultValue = "0") int page,
	                                    ModelAndView modelAndView) {
		
		Optional<User> optionalUser = userService.findByUsername(username);
		
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Page<Post> posts = postService.findByUserOrderedByDatePageable(user, page);
			
			
			modelAndView.addObject("pager", new Pager(posts));
			modelAndView.addObject("user", user);
			modelAndView.setViewName("/blog");
			return modelAndView;
		}
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		modelAndView.setViewName("/error");
		return modelAndView;
	}
}
