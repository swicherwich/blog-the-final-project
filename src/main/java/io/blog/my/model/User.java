package io.blog.my.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;


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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
