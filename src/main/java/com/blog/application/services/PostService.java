package com.blog.application.services;

import java.util.List;

import com.blog.application.payloads.PostDto;
import com.blog.application.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer categoryId, Integer userId);
	
//	PostDto updatePost(PostDto postDto, Integer postId, Integer categoryId, Integer userId);
	PostDto updatePost(PostDto postDto, Integer postId);
	
	void deletePost(Integer postId);
	
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	PostDto getPostById(Integer postId);
	
	List<PostDto> getAllPostsByCategory(Integer categoryId);
	
	List<PostDto> getAllPostsByUser(Integer userId);
	
	List<PostDto> searchPosts(String keyword);
	
}
