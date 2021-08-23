package com.i2iproject.database.models;

public class PasswordChangeAfterLoginCodePersistenceInfo {
	private int userId;
	private String email;
	private String generatedCode;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
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
