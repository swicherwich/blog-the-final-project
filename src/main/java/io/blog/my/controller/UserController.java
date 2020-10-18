package io.blog.my.controller;

import io.blog.my.model.User;
import io.blog.my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.security.Principal;

@Controller
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/sign-up")
	public ModelAndView getSignUp(ModelAndView modelAndView) {
		modelAndView.addObject("user", new User());
		modelAndView.setStatus(HttpStatus.OK);
		
		return modelAndView;
	}

	@PostMapping("/sign-up")
	public ModelAndView signUp(@Valid User user,
	                     BindingResult bindingResult,
	                     ModelAndView modelAndView) {
		
		if (userService.findByEmail(user.getEmail()).isPresent()) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		
		if (userService.findByUsername(user.getUsername()).isPresent()) {
			bindingResult
					.rejectValue("username", "error.user",
							"There is already a user registered with the username provided");
		}
		
		if (!bindingResult.hasErrors()) {
			userService.signUpUser(user);
			
			if(userService.findByUsername(user.getUsername()).isPresent()) {
				modelAndView.addObject("successMessage", "User has been registered successfully!");
				modelAndView.addObject("user", new User());
			}
		}
		
		modelAndView.setStatus(HttpStatus.OK);
		return modelAndView;
	}

	@GetMapping("/sign-in")
	public ModelAndView signIn(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("redirect:/home");
		modelAndView.setStatus(HttpStatus.OK);
		
		if(principal != null) return modelAndView;
		
		modelAndView.setViewName("/sign-in");
		return modelAndView;
	}
}
