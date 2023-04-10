package com.blog.application.services;

import java.util.List;

import com.blog.application.payloads.CategoryDto;

/**
 * 
 * @author PraptiPal
 *
 */
public interface CategoryService {

	/**
	 * 
	 * @param categoryDto
	 * @return
	 */
	
	//create a category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update a category
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	// read a category
	CategoryDto getCategoryById(Integer categoryId);
	
	// read all categories
	List<CategoryDto> getAllCategories();  
	
	//delete a category
	void deleteCategory(Integer categoryId);
	
}
