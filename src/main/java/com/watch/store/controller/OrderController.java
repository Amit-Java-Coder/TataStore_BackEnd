package com.watch.store.controller; import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.watch.store.dto.ApiResponseMessage;
import com.watch.store.dto.CreateOrderRequest;
import com.watch.store.dto.OrderDto;
import com.watch.store.dto.PageableResponse;
import com.watch.store.service.OrderService;
import jakarta.validation.Valid;
  
  @RestController  
  @RequestMapping("/orders")  
  @CrossOrigin(origins = "http://localhost:3000") 
  public class OrderController {
  
  @Autowired private OrderService service;
  
  
  //Create 
  @PostMapping
  public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request){ 
	  return new ResponseEntity<>(service.createOrder(request),HttpStatus.CREATED);
  }
  
  
  //Delete 
  @DeleteMapping("/{orderId}") 
  public ResponseEntity<ApiResponseMessage>removeOrder(@PathVariable String orderId){ 
	  service.removeOrder(orderId);
            ApiResponseMessage message=ApiResponseMessage.builder()
                                                    .message("Order removed Successfully")
                                                    .success(true)
                                                    .build(); 
            return new ResponseEntity<>(message,HttpStatus.OK);
  }
  
  
  
  
  //Get Orders of the User
  @GetMapping("/user/{userId}") 
  public ResponseEntity<List<OrderDto>> getOrderOfUser(@PathVariable String userId){
	  return new ResponseEntity<>(service.getOrderOfUser(userId),HttpStatus.OK); 
  }
  
  
  //Get All Ordered Details 
  @PreAuthorize("hasRole('ADMIN')") 
  @GetMapping 
  public ResponseEntity<PageableResponse<OrderDto>> getOders( 
  
		             @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber, 
                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                     @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
                     @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir ){ 
	  return new ResponseEntity<>(service.getOrders(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
	  }
  
  //Update Order of User
  @PutMapping("/{orderId}")
  public ResponseEntity<OrderDto> updateOrder(@PathVariable String orderId,@RequestBody OrderDto dto){
	   return new ResponseEntity<>(service.updateOrder(dto, orderId),HttpStatus.CREATED);
  }
  }
 