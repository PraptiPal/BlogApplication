package com.blog.application.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.payloads.APIResponse;
import com.blog.application.payloads.CommentDto;
import com.blog.application.services.CommentServiceImpl;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
	
	private CommentServiceImpl commentServiceImpl;
	
	public CommentController(CommentServiceImpl commentServiceImpl) {
		this.commentServiceImpl = commentServiceImpl;
	}
	
	@PostMapping("/create/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId){
		
		return new ResponseEntity<>(this.commentServiceImpl.createComment(comment, postId),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<APIResponse> deleteComment(@PathVariable Integer commentId){
		
		this.commentServiceImpl.deleteComment(commentId);
		
		return new ResponseEntity<>(new APIResponse("Comment Deleted",true),HttpStatus.OK);
	}
}
