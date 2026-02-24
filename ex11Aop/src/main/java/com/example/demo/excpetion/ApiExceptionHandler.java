package com.example.demo.excpetion;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class ApiExceptionHandler {

	//특정 예외처리
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> handleBadRequestException(BadRequestException ex){
		System.out.println("Rest Error Message " + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	//기본 예외처리
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleRequestException(Exception ex){
		System.out.println("Rest Error Message " + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}	
}
