package com.watch.store.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.watch.store.entity.OrderDetails;
import com.watch.store.entity.User;

public interface OrderRepository extends JpaRepository<OrderDetails, String> {

	public List<OrderDetails> findByUser(User user);
}
