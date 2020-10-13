package io.blog.my.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {
	
	public enum Roles {
		USER_ROLE,
		ADMIN_ROLE
	}
	
	@Column(nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
	private List<User> users;
	
	public Role() {
	}
	
	public Roles getRole() {
		return role;
	}
	
	public void setRole(Roles role) {
		this.role = role;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
