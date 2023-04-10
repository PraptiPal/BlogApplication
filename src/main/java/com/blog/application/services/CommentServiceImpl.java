package com.blog.application.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.entities.Comment;
import com.blog.application.entities.Post;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.CommentDto;
import com.blog.application.repositories.CommentRepo;
import com.blog.application.repositories.PostRepo;

@Service
public class CommentServiceImpl implements CommentService{

	private PostRepo postRepo;
	
	private CommentRepo commentRepo;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public CommentServiceImpl(CommentRepo commentRepo, PostRepo postRepo, ModelMapper modelMapper) {
		this.commentRepo = commentRepo;
		this.postRepo = postRepo;
		this.modelMapper = modelMapper;
	}
	
	private CommentDto entityToDto(Comment comment) {
		return this.modelMapper.map(comment, CommentDto.class);
	}
	
	private Comment dtoToEntity(CommentDto commentDto) {
		return this.modelMapper.map(commentDto, Comment.class);
	}
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post","PostId",postId)); 
		
		Comment comment = this.dtoToEntity(commentDto);
		
		comment.setPost(post);
		
		return this.entityToDto(this.commentRepo.save(comment));
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment","CommentId",commentId));
		
		this.commentRepo.delete(comment);
		
	}

}
