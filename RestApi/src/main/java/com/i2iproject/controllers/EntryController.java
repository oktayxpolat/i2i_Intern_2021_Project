package com.i2iproject.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.requestresponders.GetRequestResponder;

@RestController
public class EntryController {
	private ApplicationContext appContext;
	
	public EntryController( ApplicationContext appContext/*@Qualifier("GetEntryResponder") GetRequestResponder entryResponder*/) {
		this.appContext = appContext;
	}
	
	@GetMapping(path = "/api", produces = "application/vnd.collection+json")
	public CollectionResponse entryPoint() {
		GetRequestResponder entryResponder = appContext.getBean("GetEntryResponder", GetRequestResponder.class);
		return entryResponder.respond();
	}
	
}
