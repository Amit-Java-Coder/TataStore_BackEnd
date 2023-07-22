package com.watch.store.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.watch.store.dto.AddItemsToCartRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
public class Category {
	
	 @Id
     private String categoryId;
     private String title;
     private String description;
     private String coverImg;     
     @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
     private Set<Product> products=new HashSet<>();   
}
