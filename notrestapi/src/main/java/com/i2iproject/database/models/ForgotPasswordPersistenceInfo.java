package com.i2iproject.database.models;

public class ForgotPasswordPersistenceInfo {
	private String email;
	private String generatedCode;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGeneratedCode() {
		return generatedCode;
	}
	public void setGeneratedCode(String generatedCode) {
		this.generatedCode = generatedCode;
	}
	
}
