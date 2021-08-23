package com.i2iproject.responders.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RegisterResponse {
	private boolean isRegisterRequestSuccess;
	private int registerConfirmationId;

	
	public boolean isRegisterRequestSuccess() {
		return isRegisterRequestSuccess;
	}

	public void setRegisterRequestSuccess(boolean isRegisterRequestSuccess) {
		this.isRegisterRequestSuccess = isRegisterRequestSuccess;
	}

	public int getRegisterConfirmationId() {
		return registerConfirmationId;
	}

	public void setRegisterConfirmationId(int registerConfirmationId) {
		this.registerConfirmationId = registerConfirmationId;
	}
	
}
