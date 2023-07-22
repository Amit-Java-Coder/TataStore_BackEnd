package com.watch.store.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

@Configuration
public class MyConfig {

	 @Bean
	 public ModelMapper mapper() {		 
		    return new ModelMapper();
	 }
}
