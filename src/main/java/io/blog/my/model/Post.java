package io.blog.my.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post extends AbstractEntity {

	@Column(nullable = false)
	@Length(min = 3, message = "*Your title must be at least 3 characters long")
	@NotEmpty(message = "*Please provide with title")
	private String title;
	
	@Column(nullable = false)
	@Length(min = 50, message = "*Your post body must be at least 50 characters long")
	@NotEmpty(message = "*Please provide with text")
	private String body;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, updatable = false)
	@CreationTimestamp
	private Date createDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = { CascadeType.REMOVE })
	private List<Comment> comments;
}
