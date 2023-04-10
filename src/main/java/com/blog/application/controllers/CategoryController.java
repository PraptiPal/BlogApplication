package com.blog.application.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.payloads.APIResponse;
import com.blog.application.payloads.CategoryDto;
import com.blog.application.services.CategoryServiceImpl;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

	private CategoryServiceImpl categotyServiceImpl;
	
	public CategoryController(CategoryServiceImpl categoryServiceImpl) {
		this.categotyServiceImpl = categoryServiceImpl;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {  
		return ResponseEntity.ok(this.categotyServiceImpl.getAllCategories());
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) { 
		
		return new ResponseEntity<>(this.categotyServiceImpl.getCategoryById(categoryId), HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory( @Valid @RequestBody CategoryDto categoryDto){
		return new ResponseEntity<CategoryDto>(this.categotyServiceImpl.createCategory(categoryDto), HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		return new ResponseEntity<CategoryDto> (this.categotyServiceImpl.updateCategory(categoryDto, categoryId),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<APIResponse> deleteCategory(@PathVariable Integer categoryId) {
		
		this.categotyServiceImpl.deleteCategory(categoryId);
		return new ResponseEntity<APIResponse>(new APIResponse("Category Deleted Successfully",true),HttpStatus.OK);
	}
}
