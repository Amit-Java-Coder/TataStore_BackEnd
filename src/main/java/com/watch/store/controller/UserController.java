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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.watch.store.dto.ApiResponseMessage;
import com.watch.store.dto.PageableResponse;
import com.watch.store.dto.UserDto;
import com.watch.store.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins  = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {
      
	   @Autowired
	   private UserService service;
	   	
	   
	   //Create
	   @PostMapping
	   public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto dto){
		   return new ResponseEntity<>( service.createUser(dto),HttpStatus.OK);
	   }
	   
	   //Update
	   @PutMapping("/{userId}")
	   public ResponseEntity<UserDto> updateUser(@PathVariable String userId,@Valid @RequestBody UserDto dto){
		   return new ResponseEntity<>(service.UpdateUser(dto, userId),HttpStatus.OK);
	   }
	   
	   //Delete
	   @DeleteMapping("/users/{userId}")
	   public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
		   //We must return response in json format,return response in string format is not a good practice.
		   //for that we can create a class as ApiResponseMessage and provide properties according to our requirement.
		   service.deleteUserById(userId);
		   ApiResponseMessage message=  ApiResponseMessage.builder().message("User is deleted Successfully...").success(true).build();
		   return new ResponseEntity<>(message,HttpStatus.OK);
	   }
	   
	   //Get All User
	   @GetMapping
	   public ResponseEntity<PageableResponse<UserDto>> getAllUser(
			   @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
			   @RequestParam(value="pageSize",defaultValue = "10",required = false)int pageSize,
			   @RequestParam(value="sortBy",defaultValue = "name",required = false)String sortBy,
			   @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir
			   )
			   {
		   return new ResponseEntity<>(service.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	   }
	   
	   //Get User By Id
	   @GetMapping("/{userId}")
	   public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
		   return new ResponseEntity<>(service.getByIdUser(userId),HttpStatus.OK);
	   }
	   
	   //Get User By Email
	   @GetMapping("/email/{email}")
	   public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
		   return new ResponseEntity<>(service.getByEmailUser(email),HttpStatus.OK);
	   }
	   
	   //Get User By Keyword Present in its Property
	   @GetMapping("/search/{keywords}")
	   public ResponseEntity<List<UserDto>> searchuser(@PathVariable String keywords){
		   return new ResponseEntity<>(service.searchUser(keywords),HttpStatus.OK);
	   }
	   
	
}
