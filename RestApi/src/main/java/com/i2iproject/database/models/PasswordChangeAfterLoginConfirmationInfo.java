package com.i2iproject.database.models;

public class PasswordChangeAfterLoginConfirmationInfo {
	private int userId;
	private String codeReceivedViaEmail;
	private String newPassword;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
