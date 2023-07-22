package com.watch.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watch.store.dto.CategoryDto;
import com.watch.store.dto.PageableResponse;
import com.watch.store.dto.ProductDto;
import com.watch.store.service.CategoryService;
import com.watch.store.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins  = "*")
public class CategoryController {
     
	@Autowired
	private CategoryService categoryService; 
	
	@Autowired
	private ProductService productService;
	
	//Create Category
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto categoryDto){
		     return new ResponseEntity<>(categoryService.createCategory(categoryDto),HttpStatus.CREATED);
	}
	
	//Update Category
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto>updateCategory( @RequestBody CategoryDto categoryDto,@PathVariable String categoryId){
		     return new ResponseEntity<>(categoryService.updateCategory(categoryDto,categoryId),HttpStatus.OK);
	}
	
    //Delete Category
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> deleteCategory(@PathVariable String categoryId){
		     categoryService.deleteCategory(categoryId);
		     return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	//Get All Category
	@GetMapping
	public ResponseEntity< PageableResponse<CategoryDto> > getAllCategory(
			   @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
			   @RequestParam(value="pageSize",defaultValue = "10",required = false)int pageSize,
			   @RequestParam(value="sortBy",defaultValue = "name",required = false)String sortBy,
			   @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir
			){
		     return new ResponseEntity<>(categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
	}
	
	//Get Category By Id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){
		return new ResponseEntity<>(categoryService.getCategoryById(categoryId),HttpStatus.OK);
	}
	
	//Create Product with category
	@PostMapping("/{categoryId}/products")
	public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable String categoryId,@RequestBody ProductDto dto){
	    return new ResponseEntity<>	(productService.createWithCategory(dto, categoryId),HttpStatus.CREATED);
	}
	
	//Update Category  of Product
	@PutMapping("/{categoryId}/products/{productId}")
	public ResponseEntity<ProductDto> updateCategoryofProduct(@PathVariable String productId,@PathVariable String categoryId){
		return new ResponseEntity<>(productService.updateCategory(productId, categoryId),HttpStatus.OK);
	}
	
	//Get all Products of Category 
	@GetMapping("/{categoryId}/products")
	public  ResponseEntity< PageableResponse<ProductDto> >getProductOfCategory(
			   @PathVariable String categoryId,
			   @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
			   @RequestParam(value="pageSize",defaultValue = "10",required = false)int pageSize,
			   @RequestParam(value="sortBy",defaultValue = "title",required = false)String sortBy,
			   @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir
			){
		
		return new ResponseEntity<>(productService.getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
}
