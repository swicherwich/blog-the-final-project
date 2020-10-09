package io.blog.my.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {
	
	public enum Roles {
		USER_ROLE
	}
	
	@Column(nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
	private List<User> users;
	
	public Role() {
	}
	
}
