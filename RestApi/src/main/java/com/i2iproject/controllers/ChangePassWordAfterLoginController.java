package com.i2iproject.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.requestresponders.GetRequestWithPathVariableResponder;
import com.i2iproject.requestresponders.PostRequestWithPathVariableResponder;
import com.i2iproject.requestresponders.models.PostRequestBody;

@RestController
public class ChangePassWordAfterLoginController {
private ApplicationContext appContext;
	
	public ChangePassWordAfterLoginController(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	
	@GetMapping(path = "/api/users/{userId}/change-account-password", produces = "application/vnd.collection+json")
	public CollectionResponse getChangeEmail(@PathVariable("userId") int userId) {
		GetRequestWithPathVariableResponder entryResponder = 
				appContext.getBean("GetChangePasswordAfterLoginResponder", GetRequestWithPathVariableResponder.class);
		return entryResponder.respond(userId);
	}
	
	@PostMapping(path = "/api/users/{userId}/change-account-password", consumes = "application/vnd.collection+json")
	public CollectionResponse postChangeEmail(@RequestBody PostRequestBody requestBody, @PathVariable("userId") int userId) {
		PostRequestWithPathVariableResponder postLoginResponder = 
				appContext.getBean("PostChangePasswordAfterLoginResponder", PostRequestWithPathVariableResponder.class);
		return postLoginResponder.respond(requestBody,userId);
	}
	
	@PostMapping(path = "/api/users/{userId}/change-account-password/confirm", consumes = "application/vnd.collection+json")
	public CollectionResponse postConfirmEmailChange(@RequestBody PostRequestBody requestBody, @PathVariable("userId") int userId) {
		PostRequestWithPathVariableResponder postLoginResponder = 
		appContext.getBean("PostConfirmChangePasswordAfterLoginResponder", PostRequestWithPathVariableResponder.class);
		return postLoginResponder.respond(requestBody,userId);
	}
}
