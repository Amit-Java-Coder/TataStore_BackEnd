package com.watch.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watch.store.entity.Cart;
import com.watch.store.entity.User;

public interface CartRepository extends JpaRepository<Cart, String>{

	    Optional<Cart> findByUser(User user);
}
