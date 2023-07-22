package com.watch.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.watch.store.entity.User;
import com.watch.store.exception.ResourceNotFoundException;
import com.watch.store.repository.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {



	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
	User user = repository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User Name not found"));
		
		return user;
	}

}
