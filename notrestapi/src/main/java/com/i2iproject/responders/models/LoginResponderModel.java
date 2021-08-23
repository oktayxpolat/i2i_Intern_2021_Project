package com.i2iproject.responders.models;

public class LoginResponderModel {
	private String msisdn;
	private String hashedPassword;
	private String jwt;
	
	public LoginResponderModel(String msisdn, String hashedPassword, String jwt) {
		this.msisdn = msisdn;
		this.hashedPassword = hashedPassword;
		this.jwt = jwt;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public String getJwt() {
		return jwt;
	}
	
	
}
