package com.watch.store.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.watch.store.entity.Cart;
import com.watch.store.entity.CartItem;
import com.watch.store.entity.Product;
import com.watch.store.entity.User;

import jakarta.persistence.Column;
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
public class ProductDto {

	private String productId;
	private String title;
	@Column(length = 10000)
	private String description;
	private double price;
	private double discountedPrice;
	private int quantity;
	private Date addedDate;
	private boolean stock;
	private boolean live;
	private String productImgName;
	private CategoryDto category;
	
}
