package com.watch.store.service;

import java.util.List;



import com.watch.store.dto.CreateOrderRequest;
import com.watch.store.dto.OrderDto;
import com.watch.store.dto.PageableResponse;
import com.watch.store.entity.OrderDetails;

public interface OrderService {
	
	  //Create Order
	  public OrderDto createOrder(CreateOrderRequest request);
	  
	  //Remove Order
	  public void removeOrder(String orderId);
	  
	  //Get Orders of Users 
	  public List<OrderDto> getOrderOfUser(String userId);
	  
	  //Update Order of User
	  public OrderDto updateOrder(OrderDto dto,String orderId);
	  
	  //Get all Orders 
	  public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
	 
}
