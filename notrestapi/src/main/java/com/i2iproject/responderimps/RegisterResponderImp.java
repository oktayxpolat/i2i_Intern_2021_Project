package com.i2iproject.responderimps;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.RegisterCodePersister;
import com.i2iproject.database.models.RegisterConfirmationId;
import com.i2iproject.responders.RegisterResponder;
import com.i2iproject.responders.models.Register;
import com.i2iproject.responders.models.RegisterResponse;
import com.i2iproject.responders.utils.SHA256Hasher;

@Component
@Scope("prototype")
public class RegisterResponderImp implements RegisterResponder{
	private RegisterCodePersister registerCodePersister;
	private SHA256Hasher hasher;
	
	private RegisterResponse registerResponse;
	private Register requestBody;
	private RegisterConfirmationId confirmationId;
	
	public RegisterResponderImp(RegisterCodePersister registerCodePersister, SHA256Hasher hasher) {
		this.registerCodePersister = registerCodePersister;
		this.hasher = hasher;
	}

	@Override
	public RegisterResponse respond(Register requestBody) {
		this.requestBody = requestBody;
		registerResponse = new RegisterResponse();
		
		hashThePasswordInRequestBody();
		persistRegisterRequest();
		actAccordingToResponseOfRegisterRequestPersist();
		
		return registerResponse;
	}
	
	private void hashThePasswordInRequestBody() {
		 String hashedPassword = hasher.getSHA256(requestBody.getPassword());
		 requestBody.setPassword(hashedPassword);
	}

	private void persistRegisterRequest() {
		confirmationId = registerCodePersister.persistRegisterRequest(requestBody);
	}
	
	private boolean isPersistRegisterRequestSuccess() {
		return confirmationId.getRegisterConfirmationId() != RegisterCodePersister.persistingRegisterCodeFails;
	}
	
	private void actAccordingToResponseOfRegisterRequestPersist() {
		if(isPersistRegisterRequestSuccess()) 
			actWhenRegisterRequestSuccess();
		else 
			actWhenRegisterRequestFails();
	}
	
	private void actWhenRegisterRequestSuccess() {
		registerResponse.setRegisterConfirmationId(confirmationId.getRegisterConfirmationId());
		registerResponse.setRegisterRequestSuccess(true);
	}

	private void actWhenRegisterRequestFails() {
		registerResponse.setRegisterRequestSuccess(false);
	}
}
