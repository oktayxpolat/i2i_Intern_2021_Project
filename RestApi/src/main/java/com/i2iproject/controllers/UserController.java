package com.i2iproject.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.requestresponders.GetRequestWithPathVariableResponder;

@RestController
public class UserController {
	private ApplicationContext appContext;

	public UserController(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	
	/*
	@GetMapping("/api/users/{userId}")
	public CollectionResponse getUser() {
		GetRequestResponder getRegisterResponder = appContext.getBean("GetUserResponder", GetRequestResponder.class);
		return getRegisterResponder.respond();
	}*/
	
	@GetMapping(path = "/api/users/{userId}/package", produces = "application/vnd.collection+json")
	public CollectionResponse getPackageInfoOfTheUser(@PathVariable("userId") int userId) {
		GetRequestWithPathVariableResponder getRegisterResponder = appContext.getBean("GetUserPackageResponder", GetRequestWithPathVariableResponder.class);
		return getRegisterResponder.respond(userId);
	}
}
