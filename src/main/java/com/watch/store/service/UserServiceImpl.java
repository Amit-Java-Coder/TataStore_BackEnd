package com.watch.store.service;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.watch.store.dto.PageableResponse;
import com.watch.store.dto.UserDto;
import com.watch.store.entity.Role;
import com.watch.store.entity.User;
import com.watch.store.exception.ResourceNotFoundException;
import com.watch.store.helper.Helper;
import com.watch.store.repository.RoleRepository;
import com.watch.store.repository.UserRepository;

@Service
public class UserServiceImpl  implements UserService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Value("${normal.role.id}")
	private String normalRoleId;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		String userId=UUID.randomUUID().toString();
		userDto.setUserId(userId);
		userDto.setPassword(encoder.encode(userDto.getPassword()));
		
		User user=new ModelMapper().map(userDto,User.class);
		Role role = roleRepository.findById(normalRoleId).get();
		user.getRoles().add(role);
		User savedUser=repository.save(user);
		UserDto newDto=new ModelMapper().map(savedUser, UserDto.class);
		return newDto;
	}

	@Override
	public PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy,String sortDir) {
		Sort sort= (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize , sort);
		Page<User> page=repository.findAll(pageable);
	    PageableResponse<UserDto> response= Helper.getPageAbleResponse(page, UserDto.class);
		return response;
	}

	@Override
	public UserDto getByIdUser(String userId) {
		User user=repository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with given Id"));
		UserDto dto=new ModelMapper().map(user, UserDto.class);
		return dto;
	}

	@Override
	public UserDto getByEmailUser(String email) {
		User user = repository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User having this email not found"));
		UserDto dto=new ModelMapper().map(user, UserDto.class);
		return dto;
	}

	@Override
	public String deleteUserById(String userId) {
	     User user=repository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with given Id"));
		 repository.delete(user);
		 return "User deleted Successfully";
	}

	@Override
	public UserDto UpdateUser(UserDto userDto, String userId) {
	   User user=repository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with given Id"));
	   user.setName(userDto.getName());
	   user.setAbout(userDto.getAbout());
	   user.setGender(userDto.getGender());
	   user.setPassword(encoder.encode(userDto.getPassword()));
	   repository.save(user);
	   UserDto dto=new ModelMapper().map(user, UserDto.class);
	   return dto;
	}

	@Override
	public List<UserDto> searchUser(String keyWord) {
		List<User> users= repository.findByNameContaining(keyWord);
		List<UserDto> dtoList= users.stream().map(user -> new ModelMapper().map(users, UserDto.class)).toList();
		return dtoList;
	}
	
}
