package io.blog.my.controller;

import io.blog.my.model.User;
import io.blog.my.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserRegistrationController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;
	
	public UserRegistrationController(UserRepository userRepository, BCryptPasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}
	
	@PostMapping("/sing-up")
	public void signUp(@RequestBody User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
	}
}
