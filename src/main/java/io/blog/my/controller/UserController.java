package io.blog.my.controller;

import io.blog.my.model.User;
import io.blog.my.model.auth.ConfirmationToken;
import io.blog.my.service.ConfirmationTokenService;
import io.blog.my.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import java.security.Principal;

@Controller
public class UserController {
	
	private final ConfirmationTokenService confirmationTokenService;
	private final UserService userService;
	
	public UserController(ConfirmationTokenService confirmationTokenService,
	                      UserService userService) {
		this.confirmationTokenService = confirmationTokenService;
		this.userService = userService;
	}
	
	@GetMapping("/sign-up")
	public String signUp(Model model) {
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
			user.setEnabled(false);
			userService.signUpUser(user);
			
			model.addAttribute("successMessage", "Confirmation email has been sent");
			model.addAttribute("user", new User());
		}
		
		return "/sign-up";
	}
	
	@GetMapping("/sign-up/confirm")
	String confirmMail(@RequestParam("token") String token) {
		Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
		optionalConfirmationToken.ifPresent(userService::confirmUser);
		return "/sign-in";
	}

	@GetMapping("/sign-in")
	public String signIn(Principal principal) {
		if(principal != null) {
			return "/home";
		}
		return "/sign-in";
	}
}
