package io.blog.my.service.impl;

import io.blog.my.model.Role;
import io.blog.my.model.Roles;
import io.blog.my.model.User;
import io.blog.my.model.auth.ConfirmationToken;
import io.blog.my.repository.RoleRepository;
import io.blog.my.repository.UserRepository;
import io.blog.my.service.ConfirmationTokenService;
import io.blog.my.service.EmailSenderService;
import io.blog.my.service.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	
	private final String url = "http://localhost:8080";
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final EmailSenderService emailSenderService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;
	
	public UserServiceImpl(UserRepository userRepository,
	                       RoleRepository roleRepository, EmailSenderService emailSenderService,
	                       BCryptPasswordEncoder bCryptPasswordEncoder,
	                       ConfirmationTokenService confirmationTokenService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
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
				"Thank you for registering. Please click on the below link to activate your account:\n" + url + "/sign-up/confirm?token="
						+ token);
		
		emailSenderService.sendEmail(mailMessage);
	}
	
	@Override
	public void signUpUser(User user) {
//		var confirmationToken = new ConfirmationToken(user);
//
//		confirmationTokenService.saveConfirmationToken(confirmationToken);
//
//		sendConfirmationMail(user.getEmail(), confirmationToken.getConfirmationToken());
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		user.setRoles(Collections.singletonList(roleRepository.findByRole(Roles.ROLE_USER.name())));
		
		userRepository.save(user);
	}
	
	@Override
	public void confirmUser(ConfirmationToken confirmationToken) {
		User user = confirmationToken.getUser();

//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		user.setRoles(Collections.singletonList(roleRepository.findByRole(Roles.ROLE_USER.name())));
		
		userRepository.save(user);
//		confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
	}
	
	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
