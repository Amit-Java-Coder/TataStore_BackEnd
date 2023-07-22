  package com.watch.store.dto;
 
  import java.util.ArrayList;
  import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.watch.store.entity.Cart;
import com.watch.store.entity.CartItem;
import com.watch.store.entity.Product;
import com.watch.store.entity.User;

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
public class OrderDto {
  
  private String orderId;
  private String orderStatus="PENDING";
  private String paymentStatus="NOTPAID"; 
  private double orderAmount; 
  private String billingAddress; 
  private String billingPhone;
  private String billingName;
  private Date orderedDate=new Date();
  private Date deliverDate;
  private UserDto user;
  private Set<OrderItemDto> items=new HashSet<OrderItemDto>();  
  }
 