package com.blog.application.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.application.payloads.APIResponse;
import com.blog.application.payloads.PostDto;
import com.blog.application.payloads.PostResponse;
import com.blog.application.services.FileService;
import com.blog.application.services.FileServiceImpl;
import com.blog.application.services.PostServiceImpl;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

	private PostServiceImpl postServiceImpl;
	
	private FileServiceImpl fileServiceImpl;
	
	@Value("${project.image}")
	private String path;
	
	public PostController(PostServiceImpl postServiceImpl, FileServiceImpl fileServiceImpl) {
		this.postServiceImpl = postServiceImpl;
		this.fileServiceImpl =  fileServiceImpl;
	}
	
	@PostMapping("/create/{userId}/{categoryId}")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
			@PathVariable Integer userId, @PathVariable Integer categoryId){
		
		return new ResponseEntity<>(postServiceImpl.createPost(postDto, categoryId, userId), HttpStatus.CREATED);
	}
	
	@GetMapping("/getPostById/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		return new ResponseEntity<>(postServiceImpl.getPostById(postId), HttpStatus.OK);
	}
	
	@GetMapping("/getAllPosts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber, 
			@RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
		return new ResponseEntity<>(postServiceImpl.getAllPosts(pageNumber, pageSize, sortBy,sortDir), HttpStatus.OK);
	}
	
	@GetMapping("/getByUser/{userId}")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		return new ResponseEntity<>(postServiceImpl.getAllPostsByUser(userId), HttpStatus.OK);
	}
	
	@GetMapping("/getByCategory/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		return new ResponseEntity<>(postServiceImpl.getAllPostsByCategory(categoryId), HttpStatus.OK);
	}
	
//	@PutMapping("/update/{postId}/{categoryId}/{userId}")
//	public ResponseEntity<PostDto> updatePost( @RequestBody PostDto postDto, @PathVariable Integer postId,
//			@PathVariable Integer categoryId, @PathVariable Integer userId){
//		return new ResponseEntity<>(postServiceImpl.updatePost(postDto, postId,categoryId,userId),HttpStatus.CREATED);
//	}
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto> updatePost( @RequestBody PostDto postDto, @PathVariable Integer postId){
		return new ResponseEntity<>(postServiceImpl.updatePost(postDto, postId),HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<APIResponse> deletePostById(@PathVariable Integer postId){
		this.postServiceImpl.deletePost(postId);
		
		return new ResponseEntity<>(new APIResponse("Post Deleted",true), HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
		
		return new ResponseEntity<>(postServiceImpl.searchPosts(keywords),HttpStatus.OK);
	}
	
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		String fileName = this.fileServiceImpl.uploadImage(path, image);
		
		PostDto postDto = this.postServiceImpl.getPostById(postId);
		
		postDto.setImgName(fileName);
		
		PostDto updatePost = this.postServiceImpl.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK); 
	}
	
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException {
		
		InputStream resource = this.fileServiceImpl.getReosurce(path, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
