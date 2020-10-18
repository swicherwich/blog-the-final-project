package io.blog.my.controller;

import io.blog.my.model.User;
import io.blog.my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
	public String getSignUp(Model model) {
		model.addAttribute("user", new User());
		return "/sign-up";
	}

	@PostMapping("/sign-up")
	public String signUp(@Valid User user,
	                            BindingResult bindingResult,
	                            Model model) {
		
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
				model.addAttribute("successMessage", "User has been registered successfully!");
				model.addAttribute("user", new User());
			}
			
		}
		
		return "/sign-up";
	}

	@GetMapping("/sign-in")
	public String signIn(Principal principal) {
		if(principal != null) {
			return "redirect:/home";
		}
		
		return "/sign-in";
	}
}
