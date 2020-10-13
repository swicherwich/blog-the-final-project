package io.blog.my.service;

import io.blog.my.model.auth.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
	void saveConfirmationToken(ConfirmationToken token);
	void deleteConfirmationToken(Long id);
	Optional<ConfirmationToken> findConfirmationTokenByToken(String token);
}
