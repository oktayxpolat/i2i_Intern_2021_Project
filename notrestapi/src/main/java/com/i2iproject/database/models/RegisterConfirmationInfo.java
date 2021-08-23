package com.i2iproject.database.models;

public class RegisterConfirmationInfo {
	private int registerConfirmationId;
	private String generatedCode;
	
	public int getRegisterConfirmationId() {
		return registerConfirmationId;
	}
	public void setRegisterConfirmationId(int registerConfirmationId) {
		this.registerConfirmationId = registerConfirmationId;
	}
	public String getGeneratedCode() {
		return generatedCode;
	}
	public void setGeneratedCode(String generatedCode) {
		this.generatedCode = generatedCode;
	}
	
	
}
