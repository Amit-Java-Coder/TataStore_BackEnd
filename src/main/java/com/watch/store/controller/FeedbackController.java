package com.watch.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.watch.store.dto.FeedbackDto;
import com.watch.store.service.FeedbackService;

@RestController
@RequestMapping("/feedbacks")
@CrossOrigin(origins  = "http://localhost:3000")
public class FeedbackController {

	 @Autowired
	 FeedbackService feedbackService;
	
	 @PostMapping("/{userId}/{productId}")
	 public ResponseEntity<FeedbackDto> createFeedback(@RequestBody FeedbackDto dto,
			                                           @PathVariable String productId,
			                                           @PathVariable String userId){ 
		 return new ResponseEntity<>(feedbackService.createFeedback(dto, userId, productId),HttpStatus.CREATED); 
	}
	 
	@GetMapping
	public ResponseEntity<List<FeedbackDto>> getAllFeedback(){
		return new ResponseEntity<>(feedbackService.getAllFeedback(),HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<List<FeedbackDto>> getFeedbackOfProduct(@PathVariable String productId){
		return new ResponseEntity<>(feedbackService.getFeedbackOfProduct(productId),HttpStatus.OK);
	}
	
	@PutMapping("/{feedbackId}")
	public ResponseEntity<FeedbackDto> updateFeedbackOfUser(@PathVariable String feedbackId,@RequestBody FeedbackDto dto){
		return new ResponseEntity<>(feedbackService.updateFeedbackOfUserOfParticularProduct(dto, feedbackId),HttpStatus.OK);
	}

	@DeleteMapping("/{feedbackId}")
	public ResponseEntity<FeedbackDto> deleteFeedbackOfUser(@PathVariable String feedbackId){
		feedbackService.deleteFeedbackOfUserOfParticularProduct(feedbackId);
		return null;
	}
	
	@GetMapping("/user/{feedbackId}")
	public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable String feedbackId){
		return new ResponseEntity<FeedbackDto>(feedbackService.getFeedackById(feedbackId),HttpStatus.OK);
	}
}
