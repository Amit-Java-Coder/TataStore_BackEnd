package com.watch.store.service;

import com.watch.store.dto.AddItemsToCartRequest;
import com.watch.store.dto.CartDto;
import com.watch.store.entity.Cart;

public interface CartService {

	
	//Add items to Cart!!
	//If Cart for User is already available then add items to the Cart else create one Cart for User and then add items...
	public CartDto addItemsToCart(String userId,AddItemsToCartRequest request);
	
	//Remove item from Cart
	public void removeItemFromCart(String userId,int cartItemId);
	
	//Clear Cart
	public void clearCart(String userId);
	
	//Get Cart of User
	public CartDto getCartOfUser(String userId);
}
