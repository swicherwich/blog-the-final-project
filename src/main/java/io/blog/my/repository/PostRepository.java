package io.blog.my.repository;

import io.blog.my.model.Post;
import io.blog.my.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Page<Post> findByUserOrderByCreateDateDesc(User user, Pageable pageable);
	Page<Post> findAllByOrderByCreateDateDesc(Pageable pageable);
	Optional<Post> findById(Long id);
}
