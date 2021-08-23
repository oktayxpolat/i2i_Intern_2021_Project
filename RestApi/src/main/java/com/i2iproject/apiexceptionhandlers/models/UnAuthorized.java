package com.i2iproject.apiexceptionhandlers.models;

import org.springframework.http.HttpStatus;

public class UnAuthorized {
	private final String message;
	private final HttpStatus httpStatus;
	
	public UnAuthorized(String message, HttpStatus httpStatus) {
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
