package com.blog.application.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
/**
 * 
 * @author PraptiPal
 *
 */
public class CategoryDto {

	private Integer categoryId;
	
	@NotEmpty
	@Size(min = 3, max = 4)
	private String categoryTitle;
	
	@NotEmpty
	@Size(min = 3, max = 50)
	private String categoryDescription;
}
