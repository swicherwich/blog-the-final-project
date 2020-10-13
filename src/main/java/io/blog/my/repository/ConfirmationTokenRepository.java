package io.blog.my.repository;

import io.blog.my.model.auth.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
	Optional<ConfirmationToken> findConfirmationTokenByConfirmationToken(String token);
}
