package com.blog.application.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.blog.application.payloads.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> resourceNotFoundException(ResourceNotFoundException e){
		String message = e.getMessage();
		APIResponse apiResponse = new APIResponse(message,false);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException e){
		
		Map <String,String> map = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			map.put(fieldName, message);
		});
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleMethodArgsNotValidException(Exception e){
		String message = e.getMessage();
		return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<APIResponse> handleApiException(ApiException e){
		String message = e.getMessage();
		APIResponse apiResponse = new APIResponse(message,true);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
}
