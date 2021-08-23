package com.i2iproject.apiexceptionhandlers.models;

import org.springframework.http.HttpStatus;

public class BadRequest {
	private final String message;
	private final HttpStatus httpStatus;
	
	public BadRequest(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
