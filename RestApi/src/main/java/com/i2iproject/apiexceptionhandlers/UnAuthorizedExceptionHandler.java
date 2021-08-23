package com.i2iproject.apiexceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.i2iproject.apiexceptionhandlers.models.BadRequest;
import com.i2iproject.apiexceptions.UnAuthorizedException;

@ControllerAdvice
public class UnAuthorizedExceptionHandler {
	
	@ExceptionHandler(value = {UnAuthorizedException.class})
	public ResponseEntity<Object> handleBadRequestException(UnAuthorizedException unAuthorizedException){
		final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
		BadRequest responseBody = new BadRequest(unAuthorizedException.getMessage(), httpStatus);
		return new ResponseEntity<>(responseBody,httpStatus);
	}
	
}
