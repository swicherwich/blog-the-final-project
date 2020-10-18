package io.blog.my.controller;

import io.blog.my.model.Post;
import io.blog.my.model.User;
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
public class PostController {
	
	private final PostService postService;
	private final UserService userService;

	@Autowired
	public PostController(PostService postService, UserService userService) {
		this.postService = postService;
		this.userService = userService;
	}
	
	@GetMapping("/newPost")
	public ModelAndView newPost(Principal principal,
	                      ModelAndView modelAndView) {
		Optional<User> user = userService.findByUsername(principal.getName());
		
		if (user.isPresent()) {
			Post post = new Post();
			post.setUser(user.get());
			
			modelAndView.setViewName("/newPost");
			modelAndView.addObject("post", post);
			
			return modelAndView;
		}
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		modelAndView.setViewName("/error");
		return modelAndView;
	}
	
	@PostMapping("/newPost")
	public ModelAndView createNewPost(@Valid Post post,
	                            BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("newPost");
		
		if (bindingResult.hasErrors()) {
			return modelAndView;
		}
		
		postService.save(post);
		modelAndView.setViewName("redirect:/blog/" + post.getUser().getUsername());
		return modelAndView;
	}
	
	@GetMapping("/editPost/{id}")
	public ModelAndView editPostWithId(@PathVariable Long id,
	                             Principal principal,
	                                   ModelAndView modelAndView) {
		
		Optional<Post> optionalPost = postService.findForId(id);
		
		if (optionalPost.isPresent()) {
			if (isPrincipalOwnerOfPost(principal, optionalPost.get())) {
				
				modelAndView.setStatus(HttpStatus.OK);
				modelAndView.addObject("post", optionalPost.get());
				modelAndView.setViewName("/newPost");
				return modelAndView;
			}
			modelAndView.setStatus(HttpStatus.FORBIDDEN);
			modelAndView.setViewName("/403");
			return modelAndView;
		}
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		modelAndView.setViewName("/error");
		return modelAndView;
	}
	
	@GetMapping("/post/{id}")
	public ModelAndView getPostWithId(@PathVariable Long id,
	                            Principal principal,
	                                  ModelAndView modelAndView) {
		Optional<Post> optionalPost = postService.findForId(id);
		
		modelAndView.setStatus(HttpStatus.OK);
		
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			modelAndView.addObject("post", post);
			
			if (isPrincipalOwnerOfPost(principal, post))
				modelAndView.addObject("username", principal.getName());
			
			modelAndView.setViewName("/post");
			return modelAndView;
		}
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		modelAndView.setViewName("/error");
		return modelAndView;
	}
	
	@PostMapping("/deletePost/{id}")
	public ModelAndView deletePostWithId(@PathVariable Long id,
	                               Principal principal) {
		Optional<Post> optionalPost = postService.findForId(id);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/home");
		modelAndView.setStatus(HttpStatus.OK);
		
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			
			if (isPrincipalOwnerOfPost(principal, post)) {
				postService.delete(post);
				return modelAndView;
			}
			modelAndView.setStatus(HttpStatus.FORBIDDEN);
			modelAndView.setViewName("/403");
			return modelAndView;
		}
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		modelAndView.setViewName("/error");
		return modelAndView;
	}
	
	private boolean isPrincipalOwnerOfPost(Principal principal, Post post) {
		return principal != null && principal.getName().equals(post.getUser().getUsername());
	}
}
