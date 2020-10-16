package io.blog.my.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String role;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
	private List<User> users;
	
	public Role() {
	}
	
	public Role(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String  role) {
		this.role = role;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
