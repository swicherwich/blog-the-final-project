package io.blog.my.service.impl;

import io.blog.my.model.auth.ConfirmationToken;
import io.blog.my.repository.ConfirmationTokenRepository;
import io.blog.my.service.ConfirmationTokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
	
	private final ConfirmationTokenRepository confirmationTokenRepository;
	
	public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
		this.confirmationTokenRepository = confirmationTokenRepository;
	}
	
	@Override
	public void saveConfirmationToken(ConfirmationToken token) {
		confirmationTokenRepository.saveAndFlush(token);
	}
	
	@Override
	public void deleteConfirmationToken(Long id) {
		confirmationTokenRepository.deleteById(id);
	}
	
	@Override
	public Optional<ConfirmationToken> findConfirmationTokenByToken(String token) {
		return confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
	}
}
