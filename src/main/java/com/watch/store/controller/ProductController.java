package com.watch.store.controller;

import java.io.IOException;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.watch.store.dto.ApiResponseMessage;
import com.watch.store.dto.ImgResponse;
import com.watch.store.dto.PageableResponse;
import com.watch.store.dto.ProductDto;
import com.watch.store.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins  = "http://localhost:3000")
public class ProductController {

	 @Autowired
	 private ProductService productService;
	 
	 
	 //Create Product
		/*
		 * @PreAuthorize("hasRole('ADMIN')")
		 * 
		 * @PostMapping public ResponseEntity<ProductDto> createProduct(@RequestBody
		 * ProductDto dto){ return new
		 * ResponseEntity<>(productService.createProduct(dto),HttpStatus.CREATED); }
		 */
	 
	 //Update Product
	 @PutMapping("/{productId}/{categoryId}")
	 @PreAuthorize("hasRole('ADMIN')")
	 public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto dto , @PathVariable String productId, @PathVariable String categoryId){
		 return new ResponseEntity<>(productService.updateProduct(dto,productId,categoryId),HttpStatus.CREATED);
	 }
     
	 //Delete Product
	 @DeleteMapping("/{productId}")
	 @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	 public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId) throws IOException{
		 productService.deleteProductById(productId);
		 ApiResponseMessage apiResponseMessage= ApiResponseMessage.builder().message("Product deleted Succesfully").success(true).build();
		 return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
	 }
	 
	 //Get Product By Id
	 @GetMapping("/{productId}")
	 public ResponseEntity<ProductDto> getProductById(@PathVariable String productId){ 
		 return new ResponseEntity<>(productService.getProductById(productId),HttpStatus.OK);
	 }
	 
	 //Get All Product
	 @GetMapping
	 public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
			   @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
			   @RequestParam(value="pageSize",defaultValue = "10",required = false)int pageSize,
			   @RequestParam(value="sortBy",defaultValue = "title",required = false)String sortBy,
			   @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir
			 ){
		  return new ResponseEntity<>(productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
	 }
	 
	 
	 //Get All Product Those Are live
	 @GetMapping("/live")
	 public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
			   @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
			   @RequestParam(value="pageSize",defaultValue = "10",required = false)int pageSize,
			   @RequestParam(value="sortBy",defaultValue = "title",required = false)String sortBy,
			   @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir
			 ){
		  return new ResponseEntity<>(productService.getAllLive(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
	 }
	 
	 //Get Product By Title
	 @GetMapping("/search/{query}")
	 public ResponseEntity<PageableResponse<ProductDto>> findByTitle(
			   @PathVariable String query,
			   @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
			   @RequestParam(value="pageSize",defaultValue = "10",required = false)int pageSize,
			   @RequestParam(value="sortBy",defaultValue = "title",required = false)String sortBy,
			   @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir
			 ){
		  return new ResponseEntity<>(productService.findByTitle(query,pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
	 }
	 
		/*
		 * //Upload Image
		 * 
		 * @PreAuthorize("hasRole('ADMIN')")
		 * 
		 * @PostMapping("/image/{productId}") public
		 * ResponseEntity<ImgResponse>uploadProductImg(@RequestParam("productImgName")
		 * MultipartFile image,@PathVariable String productId) throws IOException{
		 * 
		 * String fileName= fileService.uploadImg(image,imgUploadPath); ProductDto
		 * dto=productService.getProductById(productId);
		 * dto.setProductImgName(fileName); ProductDto dto2=
		 * productService.updateProduct(dto, productId); ImgResponse
		 * imgResponse=ImgResponse.builder().imgName(dto2.getProductImgName()).success(
		 * true).message("Product Image Uploaded Successfully").build(); return new
		 * ResponseEntity<>(imgResponse,HttpStatus.CREATED); }
		 * 
		 * //Serve Image
		 * 
		 * @GetMapping("/image/{productId}") public void serveUserImg(@PathVariable
		 * String productId,HttpServletResponse httpServletResponse) throws IOException
		 * { ProductDto dto=productService.getProductById(productId); InputStream
		 * inputStream=fileService.getResource(imgUploadPath, dto.getProductImgName());
		 * httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		 * StreamUtils.copy(inputStream, httpServletResponse.getOutputStream()); }
		 */
}
