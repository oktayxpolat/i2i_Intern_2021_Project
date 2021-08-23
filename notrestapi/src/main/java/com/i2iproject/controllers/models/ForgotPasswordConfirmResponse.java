package com.i2iproject.controllers.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ForgotPasswordConfirmResponse {
	private boolean isPasswordChangeSuccessful;

	public boolean isPasswordChangeSuccessful() {
		return isPasswordChangeSuccessful;
	}

	public void setPasswordChangeSuccessful(boolean isPasswordChangeSuccessful) {
		this.isPasswordChangeSuccessful = isPasswordChangeSuccessful;
	}
	
	
}
