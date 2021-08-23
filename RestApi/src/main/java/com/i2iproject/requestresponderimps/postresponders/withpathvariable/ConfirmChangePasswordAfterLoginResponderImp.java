package com.i2iproject.requestresponderimps.postresponders.withpathvariable;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.apiexceptions.BadRequestException;
import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.requestresponderimps.postresponders.utils.RequestBodyVerifier;
import com.i2iproject.requestresponders.GetRequestResponder;
import com.i2iproject.requestresponders.PostRequestWithPathVariableResponder;
import com.i2iproject.requestresponders.models.PostRequestBody;

@Component("PostConfirmChangePasswordAfterLoginResponder")
@Scope("prototype")
public class ConfirmChangePasswordAfterLoginResponderImp implements PostRequestWithPathVariableResponder{
	private ApplicationContext appContext;
	private RequestBodyVerifier bodyVerifier;
	
	public ConfirmChangePasswordAfterLoginResponderImp(ApplicationContext appContext, RequestBodyVerifier bodyVerifier) {
		this.appContext = appContext;
		this.bodyVerifier = bodyVerifier;
	}

	@Override
	public CollectionResponse respond(PostRequestBody requestBody, Object pathVariables) {
		checkRequestBody(requestBody);
		return produceResponse();
	}
	
	private void checkRequestBody(PostRequestBody requestBody) {
		String[] namesWhichAreSupposedToBeIncludedInTheBody = {"codeReceivedViaEmail", "newPassword"};
		boolean isBodyCorrect = bodyVerifier.
				isRequstBodyContainsCorrectNames(requestBody, namesWhichAreSupposedToBeIncludedInTheBody);
		if(!isBodyCorrect)
			throw new BadRequestException("REQUEST BODY MUST CONTAIN:" + 
					Arrays.toString(namesWhichAreSupposedToBeIncludedInTheBody) + 
					" AND THE VALUE MUST BE PROVIDED FOR THOSE ATTIBUTES"
					);
	}
	
	private CollectionResponse produceResponse() {
		GetRequestResponder getLoginResponder = appContext.getBean("GetLoginResponder", GetRequestResponder.class);
		return getLoginResponder.respond();
	}

}
