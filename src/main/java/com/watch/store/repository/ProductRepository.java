package com.watch.store.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.watch.store.entity.Category;
import com.watch.store.entity.Product;


public interface ProductRepository  extends JpaRepository<Product, String>{

	  public Page<Product> findByTitleContaining(String subTtitle,Pageable pageable);
	  public Page<Product> findByLiveTrue(Pageable pageable);
	  
	  //For getting all Product of certain category
	  public Page<Product> findByCategory(Category category,Pageable pageable);
}
