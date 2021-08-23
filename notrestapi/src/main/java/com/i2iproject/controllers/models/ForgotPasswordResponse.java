package com.i2iproject.controllers.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ForgotPasswordResponse {
	private boolean isForgotPassswordCodeGenerated;

	public boolean isForgotPassswordCodeGenerated() {
		return isForgotPassswordCodeGenerated;
	}

	public void setForgotPassswordCodeGenerated(boolean isForgotPassswordCodeGenerated) {
		this.isForgotPassswordCodeGenerated = isForgotPassswordCodeGenerated;
	}
	
	
}
