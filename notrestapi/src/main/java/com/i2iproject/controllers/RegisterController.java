package com.i2iproject.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.i2iproject.controllers.factories.ProducerOfRegisterObjects;
import com.i2iproject.controllers.models.RegisterConfirm;
import com.i2iproject.controllers.models.RegisterConfirmResponse;
import com.i2iproject.responders.ConfirmRegisterRequestResponder;
import com.i2iproject.responders.RegisterResponder;
import com.i2iproject.responders.models.ConfirmRegister;
import com.i2iproject.responders.models.Register;
import com.i2iproject.responders.models.RegisterResponse;

@RestController
public class RegisterController {
	private ProducerOfRegisterObjects producerOfRegisterObjects;
		
	public RegisterController(ProducerOfRegisterObjects producerOfRegisterObjects) {
		this.producerOfRegisterObjects = producerOfRegisterObjects;
	}

	@PostMapping(path = "/api/register", consumes = "application/json")
	public RegisterResponse postRegister(@RequestBody Register requestBody) {
		RegisterResponse response = null;
		if(isRequiredParamatersSetInPostRegisterRequestBody(requestBody)) {
			RegisterResponder registerRequestResponder = producerOfRegisterObjects.produceRegisterResponder();
			response = registerRequestResponder.respond(requestBody);
		}
		else {
			response = new RegisterResponse();
			response.setRegisterRequestSuccess(false);
		}
		return response;
	}
	
	private boolean isRequiredParamatersSetInPostRegisterRequestBody(Register requestBody) {
		return requestBody.getEmail() != null && 
			   requestBody.getLastName() != null && 
			   requestBody.getName() != null &&
			   requestBody.getMsisdn() != null &&
			   requestBody.getPassword() != null;
	}
	
	@PostMapping(path = "/api/register/{registerId}/confirm", consumes = "application/json")
	public RegisterConfirmResponse confirmRegister(@RequestBody RegisterConfirm requestBody, 
			@PathVariable("registerId") int incomingRegisterId) {
		RegisterConfirmResponse response = new RegisterConfirmResponse();
		if(isRequiredParametersSetInPostConfirmRequestBody(requestBody)) {
			ConfirmRegister registerConfirmationInfo = new ConfirmRegister(
					requestBody.getCodeReceivedViaEmail(),
					incomingRegisterId
			);
			ConfirmRegisterRequestResponder registerConfirmer = producerOfRegisterObjects.produceRegisterConfirmer();
			boolean isRegisterCompleted = registerConfirmer.confirmRegisterRequest(registerConfirmationInfo);
			response.setRegisterSuccess(isRegisterCompleted);
		}
		else
			response.setRegisterSuccess(false);
		return response;
	}

	private boolean isRequiredParametersSetInPostConfirmRequestBody(RegisterConfirm requestBody) {
		return requestBody.getCodeReceivedViaEmail() != null;
	}
	
	
}
