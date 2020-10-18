package io.blog.my.service.impl;

import io.blog.my.model.Post;
import io.blog.my.model.User;
import io.blog.my.repository.PostRepository;
import io.blog.my.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
	
	private final PostRepository postRepository;
	private final int MAX_POSTS_ON_PAGE = 5;
	
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	@Override
	public Optional<Post> findForId(Long id) {
		return postRepository.findById(id);
	}
	
	@Override
	public Page<Post> findByUserOrderedByDatePageable(User user, int page) {
		return postRepository.findByUserOrderByCreateDateDesc(user, PageRequest.of(subtractPageByOne(page), MAX_POSTS_ON_PAGE));
	}
	
	@Override
	public Page<Post> findAllOrderedByDatePageable(int page) {
		return postRepository.findAllByOrderByCreateDateDesc(PageRequest.of(subtractPageByOne(page), MAX_POSTS_ON_PAGE));
	}
	
	@Override
	public void save(Post post) {
		postRepository.saveAndFlush(post);
	}
	
	@Override
	public void delete(Post post) {
		postRepository.delete(post);
	}
	
	private int subtractPageByOne(int page){
		return (page < 1) ? 0 : page - 1;
	}
}
