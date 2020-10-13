package io.blog.my.controller;

import io.blog.my.model.User;
import io.blog.my.model.auth.ConfirmationToken;
import io.blog.my.repository.ConfirmationTokenRepository;
import io.blog.my.repository.UserRepository;
import io.blog.my.service.ConfirmationTokenService;
import io.blog.my.service.UserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {
	
	private final ConfirmationTokenService confirmationTokenService;
	private final UserService userService;
	
	public UserController(ConfirmationTokenService confirmationTokenService,
	                      UserService userService) {
		this.confirmationTokenService = confirmationTokenService;
		this.userService = userService;
	}
	
	@GetMapping("/sign-up")
	public String registration(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@PostMapping("/sign-up")
	public String signUp(@Valid User user, BindingResult bindingResult, Model model) {
		userService.signUpUser(user);
		
		model.addAttribute("successMessage", "User has been registered successfully");
		model.addAttribute("user", new User());
		
		return "redirect:/sign-up";
	}
	
	@GetMapping("/sign-up/confirm")
	String confirmMail(@RequestParam("token") String token) {
		
		Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
		
		optionalConfirmationToken.ifPresent(userService::confirmUser);
		
		return "redirect:/sign-up";
	}
}
