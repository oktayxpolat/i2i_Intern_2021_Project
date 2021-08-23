package com.i2iproject.requestresponderimps.postresponders.withpathvariable;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.requestresponders.GetRequestResponder;
import com.i2iproject.requestresponders.PostRequestWithPathVariableResponder;
import com.i2iproject.requestresponders.models.PostRequestBody;

@Component("PostConfirmRegisterResponder")
@Scope("prototype")
public class RegisterConfirmResponder implements PostRequestWithPathVariableResponder{
	private ApplicationContext appContext;
	
	public RegisterConfirmResponder(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	
	@Override
	public CollectionResponse respond(PostRequestBody requestBody, Object pathVariables) {
		//Do post confirm action and return login page
		GetRequestResponder loginResponder = appContext.getBean("GetLoginResponder", GetRequestResponder.class);
		return loginResponder.respond();
	}	
}
