
  package com.watch.store.repository;
  
  import org.springframework.data.jpa.repository.JpaRepository;  
  import com.watch.store.entity.OrderItem;
  
  public interface OrderItemRepository extends JpaRepository<OrderItem,Integer>{
  
  }
 
