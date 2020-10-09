package io.blog.my.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

	@Email(message = "*Please provide with valid email address")
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false, unique = true)
	@Length(min = 3, message = "*Your username must be at least 3 characters long")
	@NotEmpty(message = "*Please provide your username")
	private String username;
	
	@Column(nullable = false, unique = true)
	@Length(min = 8, message = "*Your password must be at least 8 characters long")
	@NotEmpty(message = "*Please provide your password")
	private String password;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			
	)
	private Set<Role> roles;
	
	public User() {
	
	}
	
}
