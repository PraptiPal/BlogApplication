package com.blog.application.services;

import java.util.List;


import com.blog.application.payloads.UserDTO;

public interface UserService {

	public UserDTO registerNewUser(UserDTO userDTO);
	
	public UserDTO createUser(UserDTO user);
	
	public UserDTO updateUser(UserDTO user, Integer userID);
	
	public UserDTO getUserById(Integer userID);
	
	public List<UserDTO> getAllUsers();
	
	public void deleteUser(Integer userId);
}
