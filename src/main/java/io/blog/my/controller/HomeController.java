package io.blog.my.controller;

import io.blog.my.model.Post;
import io.blog.my.service.PostService;
import io.blog.my.util.Pager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	private final PostService postService;
	
	public HomeController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("/home")
	public String home(@RequestParam(defaultValue = "0") int page, Model model) {
		
		Page<Post> posts = postService.findAllOrderedByDatePageable(page);
		Pager pager = new Pager(posts);
		
		model.addAttribute("pager", pager);
		
		return "/home";
	}
}