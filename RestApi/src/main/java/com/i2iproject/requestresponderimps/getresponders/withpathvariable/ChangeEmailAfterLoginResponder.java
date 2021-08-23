/*
package com.i2iproject.requestresponderimps.getresponders.withpathvariable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.builders.CollectionBuilder;
import com.i2iproject.builders.TemplateBuilder;
import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.builders.models.TemplateData;
import com.i2iproject.requestresponders.GetRequestWithPathVariableResponder;

@Component("GetChangeEmailAfterLoginResponder")
@Scope("prototype")
public class ChangeEmailAfterLoginResponder implements GetRequestWithPathVariableResponder{
	private CollectionBuilder collectionBuilder;
	private TemplateBuilder templateBuilder;
	
	public ChangeEmailAfterLoginResponder(CollectionBuilder collectionBuilder, 
			TemplateBuilder templateBuilder) {
		this.collectionBuilder = collectionBuilder;
		this.templateBuilder = templateBuilder;
	}

	@Override
	public CollectionResponse respond(Object pathVariables) {
		//use userChecker to verify user exist
		collectionBuilder.addHref("/api/users/5/change-account-email");
		collectionBuilder.addVersion("3.12.0");
		addTemplate();
		return collectionBuilder.getConstructedCollection();
	}

	private void addTemplate() {
		addCurrentEmailToTemplate();
		collectionBuilder.addTemplate(templateBuilder.getConstructedTemplate());
	}

	private void addCurrentEmailToTemplate() {
		final String name = "currentEmail";
		templateBuilder.addTemplateData(new TemplateData(name));
	}

}
*/