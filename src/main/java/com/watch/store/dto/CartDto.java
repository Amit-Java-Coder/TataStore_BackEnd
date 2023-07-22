package com.watch.store.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class CartDto {

	private String cartId;
	private UserDto user;
	private Date createdAt;
	private Set<CartItemDto> items =new HashSet<>();	
	
}
