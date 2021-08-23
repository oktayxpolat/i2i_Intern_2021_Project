package com.i2iproject.apiexceptions;

public class BadRequestException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5993123616931793682L;

	/**
	 * 
	 */
	
	public BadRequestException(String message) {
		super(message);
	}
}
