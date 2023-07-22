  package com.watch.store.dto;  
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
  public class OrderItemDto {
  
  private int orderItemId; 
  private int quantity;
  private double totalPrice;
  private ProductDto product;
   
  }
 