package com.watch.store.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.store.dto.AddItemsToCartRequest;
import com.watch.store.dto.CartDto;
import com.watch.store.dto.CartItemDto;
import com.watch.store.entity.Cart;
import com.watch.store.entity.CartItem;
import com.watch.store.entity.Product;
import com.watch.store.entity.User;
import com.watch.store.exception.BadApiRequest;
import com.watch.store.exception.ResourceNotFoundException;
import com.watch.store.repository.CartItemRepository;
import com.watch.store.repository.CartRepository;
import com.watch.store.repository.ProductRepository;
import com.watch.store.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService{
 
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private ModelMapper mapper;
	
	
	@Override
	public CartDto addItemsToCart(String userId, AddItemsToCartRequest request) {
		int quantity=request.getQuantity();
		String productId=request.getProductId();
		
		if(quantity<=0) {
			throw new BadApiRequest("Requested quantity is not valid!!!");
		}
		
		Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Id is not valid"));
		
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Id is not valid"));
		
		//This try catch block will check weather cart present or not if not then will create one...
		Cart cart =  null; 
		try {
			
			cart=cartRepository.findByUser(user).get();
			
		}catch (NoSuchElementException e) {
			cart=new Cart();
			cart.setCartId(UUID.randomUUID().toString());
			cart.setUser(user);
			cart.setCreatedAt(new Date());
		}	
		
		//Perform Cart Operation
		//If CartItem is already present then update...
		//As we are going to alter the value of updated in map function we can not declare data-type as primitive...
		//So we will use AtomitRefernce class for this...
		AtomicReference<Boolean> updated=new AtomicReference<>(false);
		Set<CartItem>cartItems= cart.getItems();
		cartItems= cartItems.stream().map((item)->{
			if(item.getProduct().getProductId().equals(productId) && quantity==1) {
				//Item already present in cart...
				//Adding
				item.setQuantity(item.getQuantity()+1);
				item.setTotalPrice(item.getQuantity()*item.getProduct().getDiscountedPrice());
				updated.set(true);
			}
			if(item.getProduct().getProductId().equals(productId) && quantity== 2) {
				//Item already present in cart...
				//Subtracting
				item.setQuantity(item.getQuantity()-1);
				item.setTotalPrice(item.getQuantity()*item.getProduct().getDiscountedPrice());
				updated.set(true);
			}
			return item;
		}).collect(Collectors.toSet());
		//cart.setItems(updatedCartItems);
		
		
		//Create items
		if(!updated.get()) {
			CartItem cartItem= CartItem.builder()
		                           .quantity(quantity)
		                           .totalPrice(quantity*product.getDiscountedPrice())
		                           .cart(cart)
		                           .product(product)
		                           .build();		
		  
			cart.getItems().add(cartItem);
		}
		cart.setUser(user);
		Cart updatedCart= cartRepository.save(cart);
		return mapper.map(updatedCart, CartDto.class);
	}

	@Override
	public void removeItemFromCart(String userId, int cartItemId) {
		
		CartItem cartItem= cartItemRepository.findById(cartItemId).orElseThrow(()->new ResourceNotFoundException("CartItemId is not valid!!!"));
	    cartItemRepository.delete(cartItem);
	}

	@Override
	public void clearCart(String userId) {
		
		User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("UserId is not valid"));
		Cart cart= cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart is not valid"));
		cart.getItems().clear();
		cartRepository.save(cart);
	}
    
	@Override
	public CartDto getCartOfUser(String userId) {
		
		User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("UserId is not valid"));
		Cart cart= cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart is not valid"));
		return mapper.map(cart, CartDto.class);
	}

}
