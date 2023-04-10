package com.blog.application.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.application.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	
	private Integer postId;
	
	@NotEmpty
	@Size(min = 10, max = 40, message = "Title must contain atleast 10 characters and not more than 40 characters")
	private String title;
	
	@NotEmpty
	@Size(min = 100, max = 1000, message = "Post content should contain atleast 100 characters and not more than 1000 characters")
	private String content;
	
	private String imgName;

	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDTO user;
	
	private Set<CommentDto> comments = new HashSet<>();
	
}
