package com.i2iproject.apiexceptions;

public class UnAuthorizedException extends RuntimeException{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4837560658031519449L;

	public UnAuthorizedException(String message) {
		super(message);
	}
}
