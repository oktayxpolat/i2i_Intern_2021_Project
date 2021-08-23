package com.i2iproject.requestresponderimps.postresponders;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.apiexceptions.BadRequestException;
import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.requestresponderimps.postresponders.utils.RequestBodyVerifier;
import com.i2iproject.requestresponders.GetRequestResponder;
import com.i2iproject.requestresponders.PostRequestResponder;
import com.i2iproject.requestresponders.models.PostRequestBody;

@Component("PostChangeForgettenPasswordResponder")
@Scope("prototype")
public class ChangeForgettenPasswordResponderImp implements PostRequestResponder{
	private ApplicationContext appContext;
	private RequestBodyVerifier bodyVerifier;

	public ChangeForgettenPasswordResponderImp(ApplicationContext appContext, RequestBodyVerifier bodyVerifier) {
		this.appContext = appContext;
		this.bodyVerifier = bodyVerifier;
	}

	@Override
	public CollectionResponse respond(PostRequestBody requestBody) {
		checkRequestBody(requestBody);
		return produceResponse();
	}

	private void checkRequestBody(PostRequestBody requestBody) {
		String[] namesWhichAreSupposedToBeIncludedInTheBody = {"email","codeReceivedViaEmail" , "newPassword"};
		boolean isBodyCorrect = bodyVerifier.
				isRequstBodyContainsCorrectNames(requestBody, namesWhichAreSupposedToBeIncludedInTheBody);
		if(!isBodyCorrect)
			throw new BadRequestException("REQUEST BODY MUST CONTAIN:" + 
					Arrays.toString(namesWhichAreSupposedToBeIncludedInTheBody) + 
					" AND THE VALUE MUST BE PROVIDED FOR THOSE ATTIBUTES"
					);
	}
	
	private CollectionResponse produceResponse() {
		GetRequestResponder loginResponder = appContext.getBean("GetLoginResponder", GetRequestResponder.class);
		return loginResponder.respond();
	}
}
