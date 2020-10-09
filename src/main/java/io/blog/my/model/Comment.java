package io.blog.my.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends AbstractEntity {

	@Column
	@Length(min = 10, message = "*Your comment must be at least 10 characters long")
	@NotEmpty(message = "*Please provide with text")
	private String body;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, updatable = false)
	@CreationTimestamp
	private Date createDate;
	
	@ManyToOne
	@JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
	private Post post;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
	
	public Comment() {
	}
	
}
