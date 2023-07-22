package com.watch.store.service;

import java.util.List;

import com.watch.store.dto.PageableResponse;
import com.watch.store.dto.UserDto;

public interface UserService {

	   public UserDto createUser(UserDto userDto);
	   public PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy,String sortDir);
	   public UserDto getByIdUser(String userId);
	   public UserDto getByEmailUser(String email);
	   public String deleteUserById(String userId);
	   public UserDto UpdateUser(UserDto userDto,String userId);
	   public List<UserDto> searchUser(String keyWord);
}
