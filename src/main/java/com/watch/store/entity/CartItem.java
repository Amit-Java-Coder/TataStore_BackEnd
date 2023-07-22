package com.watch.store.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.watch.store.dto.AddItemsToCartRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Entity
public class CartItem {
 
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private int cartItemId;
	  
	  @ManyToOne
	  @JoinColumn(name = "product_Id")
	  private Product product;	  
	  private int quantity;	  
	  private double totalPrice;
	  
	  //Mapping Cart
	  @JsonBackReference
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name="cart_Id")
	  private Cart cart;	  
	  
}
