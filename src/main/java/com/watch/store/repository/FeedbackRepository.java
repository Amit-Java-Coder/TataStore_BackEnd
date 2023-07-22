package com.watch.store.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watch.store.dto.FeedbackDto;
import com.watch.store.entity.Feedback;
import com.watch.store.entity.Product;
import com.watch.store.entity.User;

public interface FeedbackRepository extends JpaRepository<Feedback, String>{

	   
       //Get feedback of Product 
	   List<Feedback> findByProduct(Product product);

	   //Get feedback of User 
	   List<Feedback> findByUser(User user);
}
