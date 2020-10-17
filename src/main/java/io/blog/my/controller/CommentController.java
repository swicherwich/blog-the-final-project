package io.blog.my.controller;

import io.blog.my.model.Comment;
import io.blog.my.model.Post;
import io.blog.my.model.User;
import io.blog.my.service.CommentService;
import io.blog.my.service.PostService;
import io.blog.my.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class CommentController {
	
	private final UserService userService;
	private final PostService postService;
	private final CommentService commentService;
	
	public CommentController(UserService userService, PostService postService, CommentService commentService) {
		this.userService = userService;
		this.postService = postService;
		this.commentService = commentService;
	}
	
	@PostMapping("/createComment")
	public String createNewPost(@Valid Comment comment,
	                            BindingResult bindingResult) {
		
		if (bindingResult.hasErrors())
			return "/commentForm";
		
		commentService.save(comment);
		
		Long id = comment.getPost().getId();
		return "redirect:/post/" + id;
	}
	
	@GetMapping("/commentPost/{id}")
	public String commentPostWithId(@PathVariable Long id,
	                                Principal principal,
	                                Model model) {
		
		Optional<Post> post = postService.findForId(id);
		
		if (post.isPresent()) {
			Optional<User> user = userService.findByUsername(principal.getName());
			
			if (user.isPresent()) {
				Comment comment = new Comment();
				comment.setUser(user.get());
				comment.setPost(post.get());
				
				model.addAttribute("comment", comment);
				
				return "/commentForm";
			}
			return "/error";
		}
		return "/error";
	}
	
}
