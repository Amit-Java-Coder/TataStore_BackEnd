package com.watch.store.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watch.store.dto.CategoryDto;
import com.watch.store.dto.FeedbackDto;
import com.watch.store.dto.PageableResponse;
import com.watch.store.dto.ProductDto;
import com.watch.store.dto.UserDto;
import com.watch.store.entity.Category;
import com.watch.store.entity.Product;
import com.watch.store.entity.User;
import com.watch.store.exception.ResourceNotFoundException;
import com.watch.store.helper.Helper;
import com.watch.store.repository.CategoryRepository;
import com.watch.store.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Override public ProductDto createProduct(ProductDto dto) { 
	  mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
	  Product product=mapper.map(dto, Product.class);
	  
	  String productId=UUID.randomUUID().toString();
	  product.setProductId(productId); product.setAddedDate(new Date());
	  
	  Product savedProduct=productRepository.save(product); 
	  //mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
	  return mapper.map(savedProduct, ProductDto.class); 
	  }
	 

	@Override
	public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort= (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize , sort);
		Page<Product> page=productRepository.findAll(pageable);
	    PageableResponse<ProductDto> response= Helper.getPageAbleResponse(page, ProductDto.class);
		return response;
	}

	@Override
	public ProductDto getProductById(String productId) {
		 mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		Product product= productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Id is not valid"));
		return mapper.map(product, ProductDto.class);
	}

	@Override
	public void deleteProductById(String productId) throws IOException {
		Product product= productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Id is not valid"));		
		productRepository.delete(product);		
	}

	@Override
	public ProductDto updateProduct(ProductDto dto, String productId,String categoryId) {
		  mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
	    Product product= productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Id is not valid"));
	    Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category Id is not valid"));
	    product.setPrice(dto.getPrice());
	    product.setDescription(dto.getDescription());
	    product.setDiscountedPrice(dto.getDiscountedPrice());
	    product.setQuantity(dto.getQuantity());
	    product.setTitle(dto.getTitle());
	    product.setLive(dto.isLive());
	    product.setStock(dto.isStock());
	    product.setProductImgName(dto.getProductImgName());
	    product.setCategory(category);
	    Product updatedProduct= productRepository.save(product);
		return mapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> findByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir) {
		    Sort sort= (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
			
			Pageable pageable=PageRequest.of(pageNumber, pageSize , sort);
			Page<Product> page=productRepository.findByTitleContaining(subTitle,pageable);
		    PageableResponse<ProductDto> response= Helper.getPageAbleResponse(page, ProductDto.class);
			return response;
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort= (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize , sort);
		Page<Product> page=productRepository.findByLiveTrue(pageable);
	    PageableResponse<ProductDto> response= Helper.getPageAbleResponse(page, ProductDto.class);
		return response;
	}

	@Override
	public ProductDto createWithCategory(ProductDto dto, String categoryId) {
		  mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		//Fetch the category 
		Category category2= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Id is not valid!!!"));
		
        Product product= mapper.map(dto, Product.class);
		
		String productId=UUID.randomUUID().toString();
		product.setProductId(productId);		
		product.setAddedDate(new Date());
	
		//Saving category to product
		product.setCategory(category2);
		Product savedProduct=productRepository.save(product);
		return mapper.map(savedProduct, ProductDto.class);
 
	}

	@Override
	public ProductDto updateCategory(String productId, String categoryId) {
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
	    Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Id is not valid!!!"));
	    Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Id is not valid!!!"));
	    product.setCategory(category);
	    Product savedProduct=productRepository.save(product);    
	    return mapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Id is not valid!!!"));
		Sort sort= (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());			
	    Pageable pageable=PageRequest.of(pageNumber, pageSize , sort);
		
		Page<Product>page= productRepository.findByCategory(category,pageable);
		return Helper.getPageAbleResponse(page, ProductDto.class);
	}


}
