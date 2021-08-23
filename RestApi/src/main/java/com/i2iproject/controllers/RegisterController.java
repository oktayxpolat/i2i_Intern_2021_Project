package com.i2iproject.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.requestresponders.GetRequestResponder;
import com.i2iproject.requestresponders.PostRequestResponder;
import com.i2iproject.requestresponders.PostRequestWithPathVariableResponder;
import com.i2iproject.requestresponders.models.PostRequestBody;

@RestController
public class RegisterController {
	private ApplicationContext appContext;
	
	public RegisterController(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	
	@GetMapping(path = "/api/register", produces = "application/vnd.collection+json")
	public CollectionResponse getRegister() {
		GetRequestResponder getRegisterResponder = appContext.getBean("GetRegisterResponder", GetRequestResponder.class);
		return getRegisterResponder.respond();
	}
	
	@PostMapping(path = "/api/register", consumes = "application/vnd.collection+json")
	public CollectionResponse postRegister(@RequestBody PostRequestBody requestBody) {
		PostRequestResponder postRegisterResponder = appContext.getBean("PostRegisterResponder", PostRequestResponder.class);
		return postRegisterResponder.respond(requestBody);
	}
	
	@PostMapping(path = "/api/register/{registerId}/confirm", consumes = "application/vnd.collection+json")
	public CollectionResponse confirmRegister(@RequestBody PostRequestBody requestBody, 
			@PathVariable("registerId") String registerId) {
		PostRequestWithPathVariableResponder postRegisterConfirmResponder = 
				appContext.getBean("PostConfirmRegisterResponder", PostRequestWithPathVariableResponder.class);
		return postRegisterConfirmResponder.respond(requestBody, registerId); 
	}
	
}
