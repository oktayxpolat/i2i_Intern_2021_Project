package com.i2iproject.database.models;

public class RegisterRequestInfo {
	private String name;
	private String lastName;
	private String email;
	private String msisdn;
	private String password;
	private String generatedCode;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGeneratedCode() {
		return generatedCode;
	}
	public void setGeneratedCode(String generatedCode) {
		this.generatedCode = generatedCode;
	}
	
	
}
