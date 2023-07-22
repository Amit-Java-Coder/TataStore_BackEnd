package com.watch.store.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watch.store.dto.JwtRequest;
import com.watch.store.dto.JwtResponse;
import com.watch.store.dto.UserDto;
import com.watch.store.exception.BadApiRequest;
import com.watch.store.security.JwtHelper;
import com.watch.store.service.UserDetailsServiceImpl;
import com.watch.store.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins  = "http://localhost:3000")
public class AuthController {

	 @Autowired
	 private UserDetailsService detailsService;	 
	 @Autowired
	 private ModelMapper mapper;
	 @Autowired
	 private AuthenticationManager manager;
	 @Autowired
	 private UserService service;
	 @Autowired
	 private JwtHelper helper;
	 
	 @PostMapping("/login")
	 public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
		 this.doAuthentication(request.getEmail(),request.getPassword());
		 UserDetails userDetails=detailsService.loadUserByUsername(request.getEmail());
		 String token= this.helper.generateToken(userDetails);
		 UserDto dto=mapper.map(userDetails, UserDto.class);
		 JwtResponse response= JwtResponse.builder()
		                                  .jwtToken(token)
		                                  .dto(dto)
		                                  .build();
		return new ResponseEntity<>(response,HttpStatus.OK);
		 
	 }
	 
	 private void doAuthentication(String email, String password) {
		 UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(email, password);
				 try {
					 manager.authenticate(authenticationToken);
				 }catch (BadCredentialsException e) {
					throw new BadApiRequest("Invalid Username or Password");
				}
		
	}

	@GetMapping("/current")
	 public ResponseEntity<UserDto> getCurrentUser(Principal principal){
		 String name=principal.getName();
		 return new ResponseEntity<>(mapper.map(detailsService.loadUserByUsername(name),UserDto.class),HttpStatus.OK);
	 }
}
