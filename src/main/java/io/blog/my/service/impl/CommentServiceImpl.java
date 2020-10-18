package io.blog.my.service.impl;

import io.blog.my.model.Comment;
import io.blog.my.repository.CommentRepository;
import io.blog.my.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
	
	private final CommentRepository commentRepository;
	
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	@Override
	public Comment save(Comment comment) {
		return commentRepository.saveAndFlush(comment);
	}
}
