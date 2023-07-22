package com.watch.store.dto;

import java.util.List;

import com.watch.store.entity.Cart;
import com.watch.store.entity.Product;

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
@Builder
public class CartItemDto {
	
	  private int cartItemId;
	  private ProductDto product;	  
	  private int quantity;	  
	  private double totalPrice;	  
}
