package io.blog.my.service;

import io.blog.my.model.Post;
import io.blog.my.model.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PostService {
	Optional<Post> findForId(Long id);
	Page<Post> findByUserOrderedByDatePageable(User user, int page);
	Page<Post> findAllOrderedByDatePageable(int page);
	
	void save(Post post);
	
	void delete(Post post);
}
