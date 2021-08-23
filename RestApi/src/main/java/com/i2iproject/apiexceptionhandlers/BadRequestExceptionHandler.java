package com.i2iproject.apiexceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.i2iproject.apiexceptionhandlers.models.BadRequest;
import com.i2iproject.apiexceptions.BadRequestException;

@ControllerAdvice
public class BadRequestExceptionHandler {
	
	@ExceptionHandler(value = {BadRequestException.class})
	public ResponseEntity<Object> handleBadRequestException(BadRequestException badReqException){
		final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		BadRequest responseBody = new BadRequest(badReqException.getMessage(), httpStatus);
		return new ResponseEntity<>(responseBody,httpStatus);
	}
}
