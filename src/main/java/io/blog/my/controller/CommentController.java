package io.blog.my.controller;

import io.blog.my.model.Comment;
import io.blog.my.model.Post;
import io.blog.my.model.User;
import io.blog.my.service.CommentService;
import io.blog.my.service.PostService;
import io.blog.my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class CommentController {
	
	private final UserService userService;
	private final PostService postService;
	private final CommentService commentService;
	
	@Autowired
	public CommentController(UserService userService, PostService postService, CommentService commentService) {
		this.userService = userService;
		this.postService = postService;
		this.commentService = commentService;
	}
	
	@PostMapping("/createComment")
	public ModelAndView createNewPost(@Valid Comment comment,
	                                  BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("/commentForm");
		if (bindingResult.hasErrors())
			return modelAndView;
		
		commentService.save(comment);
		
		modelAndView.setStatus(HttpStatus.OK);
		modelAndView.setViewName("redirect:/post/" + comment.getPost().getId());
		return modelAndView;
	}
	
	@GetMapping("/commentPost/{id}")
	public ModelAndView commentPostWithId(@PathVariable Long id,
	                                Principal principal,
	                                ModelAndView modelAndView) {
		
		Optional<Post> post = postService.findForId(id);
		
		if (post.isPresent()) {
			Optional<User> user = userService.findByUsername(principal.getName());
			
			if (user.isPresent()) {
				Comment comment = new Comment();
				comment.setUser(user.get());
				comment.setPost(post.get());
				
				modelAndView.setStatus(HttpStatus.OK);
				modelAndView.setViewName("/commentForm");
				modelAndView.addObject("comment", comment);
				
				return modelAndView;
			}
			modelAndView.setStatus(HttpStatus.NOT_FOUND);
			modelAndView.setViewName("/error");
			return modelAndView;
		}
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		modelAndView.setViewName("/error");
		return modelAndView;
	}
}
