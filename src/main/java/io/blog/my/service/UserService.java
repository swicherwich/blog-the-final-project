package io.blog.my.service;

import io.blog.my.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
	void signUpUser(User user);
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
}
