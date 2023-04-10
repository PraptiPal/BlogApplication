package com.blog.application.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.blog.application.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	private int id;
	
	@NotBlank(message = "Please enter a name")
	@Size(min = 3, message = "Enter atleast 3 characters")
	//@Pattern(regexp="^[a-zA-Z0-9]{3}",message="Enter valid characters and the length must be 3")
	private String name;
	
	@NotBlank
	@Email(message = "Entered email address is not valid!!")
	private String email;
	
	@NotBlank
	@Size(min = 3, max = 10, message = "Password should contain atleast 3 characters and not more than 10 characters")
	//@Pattern(regexp = "/^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,10}$/")
	private String password;
	
	@NotBlank
	@Size(min = 10, message = "Enter at least 10 characters about yourself")
	private String about;
	

	private Set<RoleDto> roles = new HashSet<>();

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty
	public void password(String password){
		this.password = password;
	}
}
