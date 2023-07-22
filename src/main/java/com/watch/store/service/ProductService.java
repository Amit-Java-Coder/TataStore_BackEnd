package com.watch.store.service;

import java.io.IOException;
import java.util.Set;

import com.watch.store.dto.FeedbackDto;
import com.watch.store.dto.PageableResponse;
import com.watch.store.dto.ProductDto;
import com.watch.store.entity.Product;

public interface ProductService {

	  public ProductDto createProduct(ProductDto dto);
	  public PageableResponse<ProductDto> getAllProduct(int pageNumber,int pageSize,String sortBy,String sortDir);
	  public ProductDto getProductById(String productId);
	  public void deleteProductById(String productId) throws IOException;
	  public ProductDto updateProduct(ProductDto dto,String productId,String categoryId);
	  public PageableResponse<ProductDto> findByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir);
	  public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);
	  
	  //Create Product With Category
	  public ProductDto createWithCategory(ProductDto dto,String categoryId);
	  
	  //Update Category of Product
	  public ProductDto updateCategory(String productId,String categoryId); 
	  
	  //Get all Products of Category 
	  public PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir);
	  
}
