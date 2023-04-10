package com.blog.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.entities.Category;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.CategoryDto;
import com.blog.application.repositories.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService{
	

	private CategoryRepo categoryRepo;
	private ModelMapper modelMapper;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepo categoryRepo, ModelMapper modelMapper) {
		this.categoryRepo = categoryRepo;
		this.modelMapper = modelMapper;
	}

	
	private CategoryDto EntityToDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}
	
	private Category DtoToEntity(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
		
	}
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.DtoToEntity(categoryDto);
		Category savedCategory = this.categoryRepo.save(category);
		return this.EntityToDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoryRepo.save(category);
		
		return this.EntityToDto(updatedCategory);
		}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		return this.EntityToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		 List<Category> allCategories= this.categoryRepo.findAll();
		 
		 List<CategoryDto> allCategoriesDto = allCategories.stream().map(category -> this.EntityToDto(category))
				 .collect(Collectors.toList());
		
		 return allCategoriesDto;
		 
		 //return this.categoryRepo.findAll();
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category","Category Id",categoryId));
		this.categoryRepo.delete(category);
		
	}

}
