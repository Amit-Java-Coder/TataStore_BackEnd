package com.watch.store.service;

import com.watch.store.dto.CategoryDto;
import com.watch.store.dto.PageableResponse;

public interface CategoryService {

	public CategoryDto createCategory(CategoryDto categoryDto);
	public CategoryDto updateCategory (CategoryDto categoryDto,String categoryId);
	public void deleteCategory(String categoryId);
	public PageableResponse<CategoryDto> getAllCategory(int pageNumber,int pageSize,String sortBy,String sortDir);
	public CategoryDto getCategoryById(String categoryId);
}
