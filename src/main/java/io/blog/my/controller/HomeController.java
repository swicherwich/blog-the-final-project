package io.blog.my.controller;

import io.blog.my.model.Post;
import io.blog.my.service.PostService;
import io.blog.my.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	private final PostService postService;
	
	@Autowired
	public HomeController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("/home")
	public ModelAndView home(@RequestParam(defaultValue = "0") int page, ModelAndView modelAndView) {
		Page<Post> posts = postService.findAllOrderedByDatePageable(page);
		
		modelAndView.setStatus(HttpStatus.OK);
		modelAndView.addObject("pager", new Pager(posts));
		
		return modelAndView;
	}
}
