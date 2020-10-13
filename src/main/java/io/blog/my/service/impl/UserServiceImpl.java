package io.blog.my.service.impl;

import io.blog.my.model.User;
import io.blog.my.model.auth.ConfirmationToken;
import io.blog.my.repository.UserRepository;
import io.blog.my.service.ConfirmationTokenService;
import io.blog.my.service.EmailSenderService;
import io.blog.my.service.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	private final String url = "http://localhost:8080";
	private final UserRepository userRepository;
	private final EmailSenderService emailSenderService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;
	
	public UserServiceImpl(UserRepository userRepository,
	                       EmailSenderService emailSenderService,
	                       BCryptPasswordEncoder bCryptPasswordEncoder,
	                       ConfirmationTokenService confirmationTokenService) {
		this.userRepository = userRepository;
		this.emailSenderService = emailSenderService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.confirmationTokenService = confirmationTokenService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(
						String.format("User with username {} has not been found", username)));
	}
	
	@Override
	public void sendConfirmationMail(String userMail, String token) {
		var mailMessage = new SimpleMailMessage();
		mailMessage.setTo(userMail);
		mailMessage.setSubject("Mail Confirmation Link!");
		mailMessage.setFrom("gitgilfoyle@gmail.com");
		mailMessage.setText(
				"Thank you for registering. Please click on the below link to activate your account." + url + "/sign-up/confirm?token="
						+ token);
		
		emailSenderService.sendEmail(mailMessage);
	}
	
	@Override
	public void signUpUser(User user) {
		final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		
		user.setPassword(encryptedPassword);
		
		var confirmationToken = new ConfirmationToken(user);
		
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		
		sendConfirmationMail(user.getEmail(), confirmationToken.getConfirmationToken());
	}
	
	@Override
	public void confirmUser(ConfirmationToken confirmationToken) {
		final User user = confirmationToken.getUser();
		
		user.setEnabled(true);
		
		userRepository.save(user);
		
		confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
	}
	
}
