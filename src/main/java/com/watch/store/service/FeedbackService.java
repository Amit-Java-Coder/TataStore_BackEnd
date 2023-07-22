package com.watch.store.service;

import java.util.List;

import com.watch.store.dto.FeedbackDto;
import com.watch.store.entity.Product;

public interface FeedbackService {

	
	//Create Feedback
	public FeedbackDto createFeedback(FeedbackDto dto,String userId,String productId);
	
	//Get all Feedback
	public List<FeedbackDto> getAllFeedback();
	
	//Get Feedback of Product
	public List<FeedbackDto> getFeedbackOfProduct(String productId);
	
	//Update Feedback of User on a particular Product
	public  FeedbackDto updateFeedbackOfUserOfParticularProduct(FeedbackDto dto,String feedbackId);
	
	//Delete Feedback of User on a particular Product
	public  void deleteFeedbackOfUserOfParticularProduct(String feedbackId);
	
	//Get Feedback by FeedbackId
	public FeedbackDto getFeedackById(String feedbackId);
	
	//Get Feedback of User
	public List<FeedbackDto> getFeedbackOfUser(String userId);
}
