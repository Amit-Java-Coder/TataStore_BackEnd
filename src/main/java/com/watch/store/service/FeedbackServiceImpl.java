package com.watch.store.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.store.dto.FeedbackDto;
import com.watch.store.entity.Feedback;
import com.watch.store.entity.Product;
import com.watch.store.entity.User;
import com.watch.store.exception.ResourceNotFoundException;
import com.watch.store.repository.FeedbackRepository;
import com.watch.store.repository.ProductRepository;
import com.watch.store.repository.UserRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService{

	@Autowired
	FeedbackRepository feedbackRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ModelMapper mapper;
	
	@Override
	public FeedbackDto createFeedback(FeedbackDto dto,String userId,String productId) {
		Product product= productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Id is not valid"));
		User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Id is not valid"));
		
		Feedback feedback=mapper.map(dto, Feedback.class);
		
		String feedbackId=UUID.randomUUID().toString();
		feedback.setFeedbackId(feedbackId);
		feedback.setProduct(product);
		feedback.setUser(user);
		Feedback savedFeedback=feedbackRepository.save(feedback);
		FeedbackDto dto2=mapper.map(savedFeedback, FeedbackDto.class);
		return dto2;
	}

	@Override
	public List<FeedbackDto> getAllFeedback() {
	    List<Feedback>feedbacks = feedbackRepository.findAll();
	    List<FeedbackDto> dtos=feedbacks.stream().map((feedback)->mapper.map(feedback, FeedbackDto.class)).collect(Collectors.toList());
		return dtos;
	}

	@Override
	public List<FeedbackDto> getFeedbackOfProduct(String productId) {
		Product product= productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Id is not valid"));
		List<Feedback> feedbacks = feedbackRepository.findByProduct(product);
		List<FeedbackDto> dtos=feedbacks.stream().map((feedback)->mapper.map(feedback, FeedbackDto.class)).collect(Collectors.toList());
		return dtos;
	}

	@Override
	public FeedbackDto updateFeedbackOfUserOfParticularProduct(FeedbackDto dto,String feedbackId) {
	    Feedback feedback=feedbackRepository.findById(feedbackId).orElseThrow(()->new ResourceNotFoundException("Feedback Id is not valid"));
		feedback.setRatings(dto.getRatings());
		feedback.setReview(dto.getReview());
		Feedback feedback2=feedbackRepository.save(feedback);
	    return mapper.map(feedback2, FeedbackDto.class);
	}

	@Override
	public void deleteFeedbackOfUserOfParticularProduct(String feedbackId) {
	   Feedback feedback=feedbackRepository.findById(feedbackId).orElseThrow(()->new ResourceNotFoundException("Feedback Id is not valid"));
	   feedbackRepository.delete(feedback);
	}

	@Override
	public FeedbackDto getFeedackById(String feedbackId) {
		Feedback feedback=feedbackRepository.findById(feedbackId).orElseThrow(()->new ResourceNotFoundException("Feedback Id is not valid"));
		return mapper.map(feedback, FeedbackDto.class);
	}

	@Override
	public List<FeedbackDto> getFeedbackOfUser(String userId) {
		User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Id is not valid"));
		List<Feedback>feedbacks=feedbackRepository.findByUser(user);
		List<FeedbackDto>dtos=feedbacks.stream().map((feedback)->mapper.map(feedback,FeedbackDto.class)).collect(Collectors.toList());
		return dtos;
	}

}
