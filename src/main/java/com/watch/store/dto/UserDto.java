package com.watch.store.dto;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;

import com.watch.store.entity.Cart;
import com.watch.store.entity.Product;
import com.watch.store.entity.Role;
import com.watch.store.validate.ImgNameValid;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

	 private String userId;
	 @Size(min = 3,max = 20,message ="Invalid name!" )
	 private String name;
	 
	 @NotBlank(message = "Email is required")
	 @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid User Email !!")
	 private String email;
	 
	 @NotBlank(message = "Password is required")
	 private String password;
	 	 
	 private String gender;
	 
	 @NotBlank(message = "Write something about yourself")
	 private String about;
	 
	 
	 private Set<RoleDto> roles=new HashSet<>();
	 
	 
	 //The above annotations we have used are for validation of property of User before storing data in database.
	 //And to perform these validation we have to add dependency Spring stater validation in our project.
	 //Then we have to provide valid annotation in our Controller class methods in which we are accepting data 
	 //we have to use it as parameter before Request-body annotation.
}
