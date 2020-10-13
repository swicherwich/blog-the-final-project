package io.blog.my.model.auth;

import io.blog.my.model.AbstractEntity;
import io.blog.my.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken extends AbstractEntity {
	
	@Column(name = "token")
	private String confirmationToken;
	
	@Column(name = "created_at")
	private LocalDateTime createdDate;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	
	public ConfirmationToken(User user) {
		this.user = user;
		this.createdDate = LocalDateTime.now();
		this.confirmationToken = UUID.randomUUID().toString();
	}

	public ConfirmationToken() {
	}
	
	public String getConfirmationToken() {
		return confirmationToken;
	}
	
	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
