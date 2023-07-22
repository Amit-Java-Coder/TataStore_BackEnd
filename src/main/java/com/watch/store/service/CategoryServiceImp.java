package com.watch.store.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.watch.store.dto.CategoryDto;
import com.watch.store.dto.PageableResponse;
import com.watch.store.entity.Category;
import com.watch.store.exception.ResourceNotFoundException;
import com.watch.store.helper.Helper;
import com.watch.store.repository.CategoryRepository;
@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired 
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		String categoryId=UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);
		
		Category category= new ModelMapper().map(categoryDto, Category.class);
		Category savedCategory=categoryRepository.save(category);
		return new ModelMapper().map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto,String categoryId) {
		Category category= categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("CategoryId not found!!"));
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		category.setCoverImg(categoryDto.getCoverImg());
		Category category2= categoryRepository.save(category);
		return new ModelMapper().map(category2, CategoryDto.class);
	}

	@Override
	public void deleteCategory(String categoryId) {
	   Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("CategoryId not found!!"));
	   categoryRepository.delete(category);
		
	}

	@Override
	public PageableResponse<CategoryDto> getAllCategory(int pageNumber,int pageSize,String sortBy,String sortDir) {
		Sort sort= (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		Page<Category>page= categoryRepository.findAll(pageable);
		PageableResponse<CategoryDto> pageableResponse=Helper.getPageAbleResponse(page,CategoryDto.class);
		return pageableResponse;
	}

	@Override
	public CategoryDto getCategoryById(String categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("CategoryId not found!!"));
		return new ModelMapper().map(category, CategoryDto.class);
	}
}
