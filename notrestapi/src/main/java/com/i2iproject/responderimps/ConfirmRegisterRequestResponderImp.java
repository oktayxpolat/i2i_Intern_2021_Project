package com.i2iproject.responderimps;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.RegisterConfirmer;
import com.i2iproject.responders.ConfirmRegisterRequestResponder;
import com.i2iproject.responders.models.ConfirmRegister;

@Component
@Scope("prototype")
public class ConfirmRegisterRequestResponderImp implements  ConfirmRegisterRequestResponder{
	private RegisterConfirmer registerConfirmer;
	
	public ConfirmRegisterRequestResponderImp(RegisterConfirmer registerConfirmer) {
		this.registerConfirmer = registerConfirmer;
	}
	
	@Override
	public boolean confirmRegisterRequest(ConfirmRegister registerConfirmationInfo) {
		return registerConfirmer.confirmRegister(registerConfirmationInfo);
	}
	

}
