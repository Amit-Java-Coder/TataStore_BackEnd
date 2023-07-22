package com.watch.store.service;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.watch.store.dto.CreateOrderRequest;
import com.watch.store.dto.OrderDto;
import com.watch.store.dto.PageableResponse;
import com.watch.store.entity.Cart;
import com.watch.store.entity.CartItem;
import com.watch.store.entity.OrderDetails;
import com.watch.store.entity.OrderItem;
import com.watch.store.entity.User;
import com.watch.store.exception.BadApiRequest;
import com.watch.store.exception.ResourceNotFoundException;
import com.watch.store.helper.Helper;
import com.watch.store.repository.CartRepository;
import com.watch.store.repository.OrderRepository;
import com.watch.store.repository.UserRepository;
  
  
  @Service public class OrderServiceImpl implements OrderService {@Autowired
  private UserRepository userRepository;
  
  @Autowired private OrderRepository orderRepository;
  
  @Autowired private ModelMapper modelMapper;
  
  @Autowired private CartRepository cartRepository;
  
  
  @Override 
  public OrderDto createOrder(CreateOrderRequest request) {
  
  String userid=request.getUserId(); 
  String cartId=request.getCartId(); 
  User user=userRepository.findById(userid).orElseThrow(()->new ResourceNotFoundException("User Id is not valid")); 
  Cart cart=cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart Id is not valid"));
  
  Set<CartItem>cartItems =cart.getItems();
  if(cartItems.size()<=0) {
	  throw new BadApiRequest("No items in the Cart");
	  }
  
   OrderDetails details=OrderDetails.builder() 
		                         .billingName(request.getBillingName())
                                 .billingAddress(request.getBillingAddress())
                                 .billingPhone(request.getBillingPhone())
                                 .orderStatus(request.getOrderStatus())
                                 .paymentStatus(request.getPaymentStatus()) 
                                 .orderedDate(new Date())
                                 .orderId(UUID.randomUUID().toString()) 
                                 .user(user) 
                                 .build();
  AtomicReference<Double> orderAmount=new AtomicReference<>(0.0);
  Set<OrderItem> orderItems=cartItems.stream().map(cartItem->
  {
	  //Converting CartItem to OrderItem
	  OrderItem orderItem=OrderItem.builder()
                                   .quantity(cartItem.getQuantity()) 
                                   .product(cartItem.getProduct())
                                   .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice()) 
                                   .details(details)
                                   .build();
  orderAmount.set((orderAmount.get() + orderItem.getTotalPrice())); 

  return orderItem;
  }).collect(Collectors.toSet());
 
  details.setItems(orderItems);
  details.setOrderAmount(orderAmount.get());
  
  cart.getItems().clear(); 
  cartRepository.save(cart); 
  OrderDetails SavedDetails=orderRepository.save(details);
  return modelMapper.map(SavedDetails, OrderDto.class); }
  
  
  @Override public void removeOrder(String orderId) {
	  OrderDetails details=orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order Id is not valid"));
  orderRepository.delete(details); 
  }
  
  @Override
  public List<OrderDto> getOrderOfUser(String userId) {
	  User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Id is not valid")); 
	  List<OrderDetails>details=orderRepository.findByUser(user);
      List<OrderDto>detailsDtos=details.stream().map(detail->modelMapper.map(detail, OrderDto.class)).collect(Collectors.toList());
      return detailsDtos; 
      }
  
  @Override 
  public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
	  Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()); 
	  Pageable pageable = PageRequest.of(pageNumber,pageSize, sort); Page<OrderDetails> page=orderRepository.findAll(pageable);
	  return Helper.getPageAbleResponse(page, OrderDto.class); 
	  }


  @Override
  public OrderDto updateOrder(OrderDto dto,String orderId) {
	OrderDetails details=orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order Id is not valid"));
	details.setBillingAddress(dto.getBillingAddress());
	details.setOrderStatus(dto.getOrderStatus());
	details.setPaymentStatus(dto.getPaymentStatus());
	details.setBillingName(dto.getBillingName());
	details.setBillingPhone(dto.getBillingPhone());
	details.setDeliverDate(dto.getDeliverDate());
	OrderDetails details2=orderRepository.save(details);
	return modelMapper.map(details2,OrderDto.class);
    }
  
  }
 