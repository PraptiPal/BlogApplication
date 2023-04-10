package com.blog.application.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.exceptions.ApiException;
import com.blog.application.payloads.JwtAuthRequest;
import com.blog.application.payloads.JwtAuthResponse;
import com.blog.application.payloads.UserDTO;
import com.blog.application.security.JwtTokenHelper;
import com.blog.application.services.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private JwtTokenHelper jwtTokenHelper;
	
	private UserDetailsService userDetailsService;
	
	private AuthenticationManager authenticationManager;
	
	@Autowired private ModelMapper modelMapper;
	
	private UserServiceImpl userServiceImpl;
	@Autowired
	public AuthController(JwtTokenHelper jwtTokenHelper,UserDetailsService userDetailsService,AuthenticationManager authenticationManager, UserServiceImpl userServiceImpl) {
	
		this.jwtTokenHelper = jwtTokenHelper;
		
		this.userDetailsService = userDetailsService;
		
		this.authenticationManager = authenticationManager;
		
		this.userServiceImpl = userServiceImpl;
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request
			) throws Exception{
		
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUserDto(modelMapper.map(userDetails,UserDTO.class));
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.authenticationManager.authenticate(authenticationToken);
		}catch(BadCredentialsException e) {
			System.out.println("Invalid Details");
			throw new ApiException("Invalid Username or password!");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerNewUser(@Valid
			@RequestBody UserDTO userDTO){
				return new ResponseEntity<>(this.userServiceImpl.registerNewUser(userDTO),
						HttpStatus.CREATED);
	}
}
