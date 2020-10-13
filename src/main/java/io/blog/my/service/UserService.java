package io.blog.my.service;

import io.blog.my.model.User;
import io.blog.my.model.auth.ConfirmationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	void signUpUser(User user);
	void sendConfirmationMail(String userMail, String token);
	void confirmUser(ConfirmationToken confirmationToken);
}
