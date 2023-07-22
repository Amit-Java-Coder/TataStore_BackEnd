package com.watch.store.dto;

import jakarta.validation.constraints.NotBlank;
import com.watch.store.dto.ProductDto;
import com.watch.store.entity.Cart;
import com.watch.store.entity.Product;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	 
     private String categoryId;
     @NotBlank(message = "Title is required")
     @Size(min = 4,max = 30,message = "Title must be within 4 and 20 characters!!")
     private String title;
     @NotBlank(message = "Description is required")
     private String description;
     private String coverImg;
     
}
