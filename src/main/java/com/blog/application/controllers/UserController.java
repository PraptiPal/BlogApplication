package com.blog.application.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.payloads.APIResponse;
import com.blog.application.payloads.UserDTO;
import com.blog.application.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService  userService;
	
	//POST
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
		
		UserDTO createUserDTO = this.userService.createUser(userDTO);
		
		return new ResponseEntity<>(createUserDTO, HttpStatus.CREATED);
	}
	
	// PUT
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Integer userId){
		
		UserDTO updatedUserDTO = this.userService.updateUser(userDTO, userId);
		
		return new ResponseEntity<>(updatedUserDTO, HttpStatus.CREATED);
	}
	
	// DELETE
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<APIResponse> deleteUser(@PathVariable Integer userId){
		
		this.userService.deleteUser(userId);
		//return new ResponseEntity(Map.of("Message","user deleted successfully"),HttpStatus.OK);
		return new ResponseEntity<APIResponse>(new APIResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
	
	
	//GET - BY ID
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId){
		
		UserDTO getUserByIdDTO = this.userService.getUserById(userId);
		
		return new ResponseEntity<>(getUserByIdDTO, HttpStatus.CREATED);
	}
	
	//GET - ALL
		@GetMapping("/")
		public ResponseEntity<List<UserDTO>> getAllUsers(){
			List<UserDTO> usersLists = this.userService.getAllUsers();
			return  ResponseEntity.ok(usersLists);
		}
}
