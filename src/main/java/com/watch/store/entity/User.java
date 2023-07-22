package com.watch.store.entity;

import java.util.ArrayList;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.ManyToAny;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.watch.store.dto.AddItemsToCartRequest;
import com.watch.store.dto.UserDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Entity
public class User  implements UserDetails{
    
	 @Id
	 private String userId;
	 private String name;
	 
	 @Column(unique =  true)
	 private String email;
	 private String password;
	 private String gender;
	 private String about;
	 
	 @OneToOne(mappedBy = "user",cascade =  CascadeType.REMOVE)
	 private Cart cart;
	 
	 @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	 private Set<Role> roles=new HashSet<>(); 
	 
	 
	 //When ever we want to delete user from user table and we also want to delete user related order or cart for that we need to use cascade = CascadeType.REMOVE
	 @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE )
	 private Set<OrderDetails> details=new HashSet<>();

	 

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authorities=this.roles.stream().map(role-> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
		return authorities;
	}


	@Override
	public String getUsername() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
