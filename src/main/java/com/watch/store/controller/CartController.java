package com.watch.store.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watch.store.dto.AddItemsToCartRequest;
import com.watch.store.dto.ApiResponseMessage;
import com.watch.store.dto.CartDto;
import com.watch.store.service.CartService;

@RestController
@RequestMapping("/carts")
@CrossOrigin(origins  = "http://localhost:3000")
public class CartController {	
    
	 @Autowired
	 private CartService cartService;
	
	 //Add item to Cart
	 @PostMapping("/{userId}")
	 public ResponseEntity<CartDto> adItemToCart(@RequestBody AddItemsToCartRequest addItemsToCartRequest,@PathVariable String userId){
		 return new ResponseEntity<>( cartService.addItemsToCart(userId, addItemsToCartRequest),HttpStatus.CREATED);
	 }
	
	 //Remove item from Cart
	 @PutMapping("/{userId}/items/{cartItemId}")
	 public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId,@PathVariable int cartItemId ){
		 cartService.removeItemFromCart(userId, cartItemId);
		 ApiResponseMessage message=ApiResponseMessage.builder().message("Item Removed").success(true).build();
		 return new ResponseEntity<>(message,HttpStatus.OK);
	 }
	 
	 //Clear Cart
	 @DeleteMapping("/{userId}")
	 public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){
		 cartService.clearCart(userId);
		 ApiResponseMessage message=ApiResponseMessage.builder().message("Cart is blank now").success(true).build();
		 return new ResponseEntity<>(message,HttpStatus.OK);
	 }
	 
	 //Get Cart
	 @GetMapping("/{userId}")
	 public ResponseEntity<CartDto> getCartByUserId(@PathVariable String userId){
		 return new ResponseEntity<>(cartService.getCartOfUser(userId),HttpStatus.OK);
	 }
}
