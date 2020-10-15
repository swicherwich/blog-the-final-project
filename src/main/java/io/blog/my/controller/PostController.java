package io.blog.my.controller;

import io.blog.my.model.Post;
import io.blog.my.model.User;
import io.blog.my.service.PostService;
import io.blog.my.service.UserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

public class PostController {
	
	private final PostService postService;
	private final UserService userService;

	public PostController(PostService postService, UserService userService) {
		this.postService = postService;
		this.userService = userService;
	}
	
	@GetMapping("/newPost")
	public String newPost(Principal principal,
	                      Model model) {
		Optional<User> user = userService.findByUsername(principal.getName());
		
		if (user.isPresent()) {
			Post post = new Post();
			post.setUser(user.get());
			
			model.addAttribute("post", post);
			
			return "/postForm";
			
		} else {
			return "/error";
		}
	}
	
	@PostMapping("/newPost")
	public String createNewPost(@Valid Post post,
	                            BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/postForm";
		} else {
			postService.save(post);
			return "redirect:/blog/" + post.getUser().getUsername();
		}
	}
	
	@GetMapping("/editPost/{id}")
	public String editPostWithId(@PathVariable Long id,
	                             Principal principal,
	                             Model model) {
		Optional<Post> optionalPost = postService.findForId(id);
		
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			
			if (isPrincipalOwnerOfPost(principal, post)) {
				model.addAttribute("post", post);
				return "/postForm";
			} else {
				return "/403";
			}
			
		} else {
			return "/error";
		}
	}
	
	@GetMapping("/post/{id}")
	public String getPostWithId(@PathVariable Long id,
	                            Principal principal,
	                            Model model) {
		Optional<Post> optionalPost = postService.findForId(id);
		
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			
			model.addAttribute("post", post);
			if (isPrincipalOwnerOfPost(principal, post)) {
				model.addAttribute("username", principal.getName());
			}
			
			return "/post";
			
		} else {
			return "/error";
		}
	}
	
	@DeleteMapping("/post/{id}")
	public String deletePostWithId(@PathVariable Long id,
	                               Principal principal) {
		Optional<Post> optionalPost = postService.findForId(id);
		
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			
			if (isPrincipalOwnerOfPost(principal, post)) {
				postService.delete(post);
				return "/home";
			} else {
				return "/403";
			}
			
		} else {
			return "/error";
		}
	}
	
	private boolean isPrincipalOwnerOfPost(Principal principal, Post post) {
		return principal != null && principal.getName().equals(post.getUser().getUsername());
	}
}
