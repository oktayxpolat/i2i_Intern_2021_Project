package com.i2iproject.responders.models;

public class ConfirmRegister {
	private String codeReceivedViaEmail;
	private int registerConfirmationId;
	
	public ConfirmRegister(String codeReceivedViaEmail, int registerConfirmationId) {
		this.codeReceivedViaEmail = codeReceivedViaEmail;
		this.registerConfirmationId = registerConfirmationId;
	}

	public String getCodeReceivedViaEmail() {
		return codeReceivedViaEmail;
	}

	public void setCodeReceivedViaEmail(String codeReceivedViaEmail) {
		this.codeReceivedViaEmail = codeReceivedViaEmail;
	}

	public int getRegisterConfirmationId() {
		return registerConfirmationId;
	}

	public void setRegisterConfirmationId(int registerConfirmationId) {
		this.registerConfirmationId = registerConfirmationId;
	}
	
	
	
	
}
