package com.i2iproject.controllers.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RegisterConfirmResponse {
	private boolean isRegisterSuccess;

	public boolean isRegisterSuccess() {
		return isRegisterSuccess;
	}

	public void setRegisterSuccess(boolean isRegisterSuccess) {
		this.isRegisterSuccess = isRegisterSuccess;
	}
	
}
