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
public class LoginController {
	private ApplicationContext appContext;

	public LoginController(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	
	@GetMapping(path = "/api/login", produces = "application/vnd.collection+json")
	public CollectionResponse getLogin() {
		GetRequestResponder getLoginResponder = appContext.getBean("GetLoginResponder", GetRequestResponder.class);
		return getLoginResponder.respond();
	}
	
	@PostMapping(path = "/api/login", consumes = "application/vnd.collection+json")
	public CollectionResponse postLogin(@RequestBody PostRequestBody requestBody) {
		PostRequestResponder postLoginResponder = appContext.getBean("PostLoginResponder", PostRequestResponder.class);
		return postLoginResponder.respond(requestBody);
	}
	
}
