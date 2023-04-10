package com.blog.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.application.entities.Role;
import com.blog.application.entities.User;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.UserDTO;
import com.blog.application.repositories.RoleRepo;
import com.blog.application.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		
		User user = this.dtoToEntity(userDTO);
		User savedUser = this.userRepo.save(user);
		return this.userTODto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userID) {
		
		User user = this.userRepo.findById(userID).orElseThrow(
				()-> new ResourceNotFoundException("User", "userId", userID));
		
		
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		
		return this.userTODto(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer userID) {
		User user = this.userRepo.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User","userId",userID));
		return this.userTODto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		
		List<UserDTO> usersDTOs = 
		users.stream().map(user -> this.userTODto(user)).collect(Collectors.toList());
		
		return usersDTOs;
	}

	@Override
	public void deleteUser(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
		 this.userRepo.delete(user);
		
	}
	
	private User dtoToEntity(UserDTO userDTO) {
		User user = this.modelMapper.map(userDTO, User.class);
		return user;
	}

	public UserDTO userTODto(User user) {
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserDTO registerNewUser(UserDTO userDTO) {
		
		User user = this.dtoToEntity(userDTO);
		
		//password encoding
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//role
		Role role = this.roleRepo.findById(502).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		
		return this.userTODto(newUser);
	}
}
