  package com.watch.store.entity;
  
  import com.watch.store.dto.AddItemsToCartRequest;

import jakarta.persistence.CascadeType; 
  import jakarta.persistence.Entity; 
  import jakarta.persistence.FetchType;
  import jakarta.persistence.GeneratedValue; 
  import jakarta.persistence.GenerationType;
  import jakarta.persistence.Id; 
  import jakarta.persistence.JoinColumn;
  import jakarta.persistence.ManyToOne; 
  import jakarta.persistence.OneToOne; 
  import lombok.AllArgsConstructor; 
  import lombok.Builder;
import lombok.Data;
import lombok.Getter; 
  import lombok.NoArgsConstructor;
  import lombok.Setter; import lombok.ToString;
  @Getter
  @Setter
  @ToString
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Entity
  public class OrderItem {
	  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO) 
  private int orderItemId;
  private int quantity; 
  private double totalPrice;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  private Product product;
  
  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)  
  @JoinColumn(name="order_id")
  private OrderDetails details;  
  
  }
 
