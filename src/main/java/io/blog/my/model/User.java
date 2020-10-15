package io.blog.my.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {

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
	private List<Role> roles;
	
	@Transient
	private Boolean locked = false;
	
	@Column(nullable = false)
	private Boolean enabled;
	
	public User() {
		this.roles = List.of(new Role(Roles.USER_ROLE));
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
	
	@Override
	public boolean isAccountNonExpired() {
		return !locked;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(Roles.USER_ROLE.name());
		return Collections.singletonList(simpleGrantedAuthority);
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
	@Override
	public String toString() {
		return "User{" +
				"email='" + email + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", roles=" + roles.toString() +
				", enabled=" + enabled +
				'}';
	}
}
