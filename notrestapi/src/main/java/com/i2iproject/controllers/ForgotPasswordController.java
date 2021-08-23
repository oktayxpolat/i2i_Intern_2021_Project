package com.i2iproject.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.i2iproject.controllers.factories.ProducerOfForgotPasswordObjects;
import com.i2iproject.controllers.models.ForgotPassword;
import com.i2iproject.controllers.models.ForgotPasswordConfirmResponse;
import com.i2iproject.controllers.models.ForgotPasswordResponse;
import com.i2iproject.responders.ConfirmPasswordChangeResponder;
import com.i2iproject.responders.ForgotPasswordResponder;
import com.i2iproject.responders.models.ForgotPasswordConfirm;

@RestController
public class ForgotPasswordController {
	private ProducerOfForgotPasswordObjects factoryOfForgotPasswordObjects;
	
	public ForgotPasswordController(ProducerOfForgotPasswordObjects factoryOfForgotPasswordObjects) {
		this.factoryOfForgotPasswordObjects = factoryOfForgotPasswordObjects;
	}

	@PostMapping(path = "/api/login/forgot-password", consumes = "application/json")
	public ForgotPasswordResponse postForgotPassword(@RequestBody ForgotPassword requestBody) {
		ForgotPasswordResponse response = new ForgotPasswordResponse();
		if(isRequiredParametersSetInForgotPasswordRequestBody(requestBody)) {
			ForgotPasswordResponder forgotPasswordResponder = factoryOfForgotPasswordObjects.produceForgotPasswordResponder();
			boolean isCodePersisted = forgotPasswordResponder.sendPasswordChangeCodeToGivenEmail(requestBody.getEmail());
			response.setForgotPassswordCodeGenerated(isCodePersisted);
		}
		else
			response.setForgotPassswordCodeGenerated(false);
		return response;
	}
	
	private boolean isRequiredParametersSetInForgotPasswordRequestBody(ForgotPassword requestBody) {
		return requestBody.getEmail() != null;
	}

	@PostMapping(path = "/api/login/forgot-password/change-password", consumes = "application/json")
	public ForgotPasswordConfirmResponse postChangePassword(@RequestBody ForgotPasswordConfirm requestBody) {
		ForgotPasswordConfirmResponse response = new ForgotPasswordConfirmResponse();
		if(isRequiredParametersSetInChangePasswordRequestBody(requestBody)) {
			ConfirmPasswordChangeResponder confirmerResponder = factoryOfForgotPasswordObjects.produceConfirmerOfForgotPasswordResponder();
			boolean hasPasswordChanged = confirmerResponder.confirmPasswordChange(requestBody);
			response.setPasswordChangeSuccessful(hasPasswordChanged);
		}
		else
			response.setPasswordChangeSuccessful(false);
		return response;
	}

	private boolean isRequiredParametersSetInChangePasswordRequestBody(ForgotPasswordConfirm requestBody) {
		return  requestBody.getEmail() != null 
				&& requestBody.getCodeReceivedViaEmail() != null 
				&& requestBody.getPassword() != null;
	}
}
