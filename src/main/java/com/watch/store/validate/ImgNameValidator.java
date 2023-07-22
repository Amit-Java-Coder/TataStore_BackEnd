package com.watch.store.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImgNameValidator implements ConstraintValidator<ImgNameValid, String>{
 
	private Logger logger=LoggerFactory.getLogger(ImgNameValid.class);
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		logger.info("Message From invalid : {}",value);
		
		if(value.isBlank()) {
			return false;
		}
		return true;
	}

}
