package com.watch.store.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.watch.store.dto.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
	 
	  private Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);

	  @ExceptionHandler(ResourceNotFoundException.class)
	  public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		  
		   logger.info("Exception handler invoked!");
		   
		   ApiResponseMessage apiResponseMessage=  ApiResponseMessage.builder().message(ex.getMessage()).success(false).build();
		   return new ResponseEntity<>(apiResponseMessage,HttpStatus.NOT_FOUND);
	  }
	  
	  @ExceptionHandler(MethodArgumentNotValidException.class)
	  public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		  
		List<ObjectError> errors=  ex.getBindingResult().getAllErrors();
		Map<String,Object> response=new HashMap<>();
		errors.stream().forEach(error -> {
			String message=error.getDefaultMessage();
			String field=((FieldError)error).getField();
            response.put(field, message);
		    });
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(BadApiRequest.class)
	  public ResponseEntity<ApiResponseMessage> hanleBadApiRequst(BadApiRequest ex){
		  
		   logger.info("Bad Api Requset!!");
		   
		   ApiResponseMessage apiResponseMessage=  ApiResponseMessage.builder().message(ex.getMessage()).success(false).build();
		   return new ResponseEntity<>(apiResponseMessage,HttpStatus.BAD_REQUEST);
	  }
}
