package com.watch.store.entity;

import java.util.Date;
import java.util.List;

import com.watch.store.dto.AddItemsToCartRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Role {
 
	@Id
	private String roleId;
	private String  roleName;	
}

