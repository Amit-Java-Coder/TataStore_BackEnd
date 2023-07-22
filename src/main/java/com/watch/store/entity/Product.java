package com.watch.store.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.watch.store.dto.AddItemsToCartRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Entity
public class Product {
    
	@Id
	private String productId;
	private String title;
	@Column(length = 10000)
	private String description;
	private double price;
	private double discountedPrice;
	private int quantity;
	private Date addedDate;
	private boolean stock;
	private boolean live;
	private String productImgName;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false,name="category_id")
	private Category category;

	@OneToMany(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.REMOVE)
	private Set<Feedback> feedbacks=new HashSet<>();
	
}
