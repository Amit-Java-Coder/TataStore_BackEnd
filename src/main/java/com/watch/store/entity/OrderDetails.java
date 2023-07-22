package com.watch.store.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.watch.store.dto.AddItemsToCartRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class OrderDetails {
 
	 @Id
	 private String orderId;
	 private String orderStatus;
	 private String paymentStatus;
	 private double orderAmount;
	 private String billingAddress;
	 private String billingPhone;
	 private String billingName;
	 private Date orderedDate;
	 private Date deliverDate;
	 
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(nullable = false,name="user_id")
	 private User user;
	 
	 @JsonManagedReference
     @OneToMany(mappedBy = "details",fetch = FetchType.EAGER,cascade =CascadeType.ALL)
	 private Set<OrderItem>items=new HashSet<OrderItem>();
		 
}
