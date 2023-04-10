package com.blog.application.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;

	private UserDTO userDto;
}
