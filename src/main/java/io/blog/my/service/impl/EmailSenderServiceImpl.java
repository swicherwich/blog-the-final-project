package io.blog.my.service.impl;

import io.blog.my.service.EmailSenderService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
	
	private final JavaMailSender javaMailSender;
	
	public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	
	@Override
	public void sendEmail(SimpleMailMessage email) {
		javaMailSender.send(email);
	}
}
