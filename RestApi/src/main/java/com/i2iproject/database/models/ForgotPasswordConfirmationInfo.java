package com.i2iproject.database.models;

public class ForgotPasswordConfirmationInfo {
	private String email;
	private String codeReceivedViaEmail;
	private String newPassword;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCodeReceivedViaEmail() {
		return codeReceivedViaEmail;
	}
	public void setCodeReceivedViaEmail(String codeReceivedViaEmail) {
		this.codeReceivedViaEmail = codeReceivedViaEmail;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
