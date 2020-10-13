package io.blog.my.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {
	void sendEmail(SimpleMailMessage mailMessage);
}
