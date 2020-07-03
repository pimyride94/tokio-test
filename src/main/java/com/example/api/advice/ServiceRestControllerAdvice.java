package com.example.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.api.advice.exception.CustomExistsException;
import com.example.api.advice.exception.CustomNotFoundException;

@RestControllerAdvice
public class ServiceRestControllerAdvice {

	@ExceptionHandler(CustomNotFoundException.class)
	public ResponseEntity<String> handleNotFoundException(CustomNotFoundException ex) {
		ResponseEntity<String> response = new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    return response;
	}
	
	@ExceptionHandler(CustomExistsException.class)
	public ResponseEntity<String> handleExistsException(CustomNotFoundException ex) {
		ResponseEntity<String> response = new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    return response;
	}
	
}
