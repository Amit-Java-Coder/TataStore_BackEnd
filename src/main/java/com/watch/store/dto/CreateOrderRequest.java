package com.watch.store.dto;

import com.watch.store.entity.Cart;
import com.watch.store.entity.Product;

import jakarta.validation.constraints.NotBlank;
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
public class CreateOrderRequest {

	  @NotBlank(message = "Cart id is required !!")
	  private String cartId;
	  @NotBlank(message = "User id is required !!")
	  private String userId;	  
	  private String orderStatus = "PENDING";
	  private String paymentStatus = "NOTPAID";
	  @NotBlank(message = "Address is required !!")
	  private String billingAddress;
	  @NotBlank(message = "Phone number is required !!")
	  private String billingPhone;
	  @NotBlank(message = "Billing name  is required !!")
	  private String billingName;	  
	  
}
