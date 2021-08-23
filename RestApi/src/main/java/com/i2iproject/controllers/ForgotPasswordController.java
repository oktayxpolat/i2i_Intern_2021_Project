package com.i2iproject.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.requestresponders.GetRequestResponder;
import com.i2iproject.requestresponders.PostRequestResponder;
import com.i2iproject.requestresponders.models.PostRequestBody;

@RestController
public class ForgotPasswordController {
	
	private ApplicationContext appContext;
	
	public ForgotPasswordController(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	@GetMapping(path = "/api/login/forgot-password", produces = "application/vnd.collection+json")
	public CollectionResponse getForgotPassword() {
		GetRequestResponder getForgotPasswordResponder = 
				appContext.getBean("GetForgotPasswordResponder", GetRequestResponder.class);
		return getForgotPasswordResponder.respond();
	}
	
	@PostMapping(path = "/api/login/forgot-password", consumes = "application/vnd.collection+json")
	public CollectionResponse postForgotPassword(@RequestBody PostRequestBody requestBody) {
		PostRequestResponder postForgotPasswordResponder = 
				appContext.getBean("PostForgotPasswordResponder", PostRequestResponder.class);
		return postForgotPasswordResponder.respond(requestBody);
	}
	
	@PostMapping(path = "/api/login/forgot-password/change-password", consumes = "application/vnd.collection+json")
	public CollectionResponse postChangePassword(@RequestBody PostRequestBody requestBody) {
		PostRequestResponder postChangeForgettenPasswordResponder = 
				appContext.getBean("PostChangeForgettenPasswordResponder", PostRequestResponder.class);
		return postChangeForgettenPasswordResponder.respond(requestBody);
	}
}
