package com.blog.application.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.application.entities.Category;
import com.blog.application.entities.Post;
import com.blog.application.entities.User;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.PostDto;
import com.blog.application.payloads.PostResponse;
import com.blog.application.repositories.CategoryRepo;
import com.blog.application.repositories.PostRepo;
import com.blog.application.repositories.UserRepo;

@Service
public class PostServiceImpl implements PostService {

	private PostRepo postRepo;
	
	private ModelMapper modelMapper;
	
	private UserRepo userRepo;
	
	private CategoryRepo categoryRepo;
	
	@Autowired
	public PostServiceImpl(PostRepo postRepo, ModelMapper modelMapper, UserRepo userRepo,CategoryRepo categoryRepo) {
		this.postRepo = postRepo;
		this.modelMapper = modelMapper;
		this.userRepo = userRepo;
		this.categoryRepo = categoryRepo;
	}
	
	private PostDto entityToDto(Post post) {
		return this.modelMapper.map(post, PostDto.class);
	}
	
	private Post postDtoToEntity(PostDto postDto) {
		return this.modelMapper.map(postDto, Post.class);
	}
	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer categoryId, Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id",userId));
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Post post = this.postDtoToEntity(postDto);
		
		post.setImgName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		return this.entityToDto(this.postRepo.save(post));
	}

//	@Override
//	public PostDto updatePost(PostDto postDto,Integer postId,Integer categoryId, Integer userId) {
//		
//		User user = this.userRepo.findById(userId)
//				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id",userId));
//		
//		Category category = this.categoryRepo.findById(categoryId)
//				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
//		
//		Post post = this.postRepo.findById(postId)
//				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id",postId));
//		
//		post.setCategory(category);
//		post.setUser(user);
//		post.setTitle(postDto.getTitle());
//		post.setContent(postDto.getContent());
//		post.setImgName(postDto.getImgName());
//		return this.entityToDto(this.postRepo.save(post));
//	}
	@Override
	public PostDto updatePost(PostDto postDto,Integer postId) {
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id",postId));
		
		Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();
		post.setCategory(category);
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImgName(postDto.getImgName());
		return this.entityToDto(this.postRepo.save(post));
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id",postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort = null;
		
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		
		List<PostDto> allPosts = pagePost.getContent().stream().map(post -> this.entityToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(allPosts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id",postId));
		return this.entityToDto(post);
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category","Category Id",categoryId));
		
		//List<Post> postsByCategory = this.postRepo.findByCategory(category);
		/*List<PostDto> postsByCategoryDto =*/ 
		return this.postRepo.findByCategory(category)
				.stream().map(post -> this.entityToDto(post) ).collect(Collectors.toList());
		//return postsByCategoryDto;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","User Id",userId));
		
		return this.postRepo.findByUser(user)
				.stream().map(post -> this.entityToDto(post)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		return this.postRepo.findByTitleContaining(keyword)
				.stream().map(post -> this.entityToDto(post)).collect(Collectors.toList());
	}

}
