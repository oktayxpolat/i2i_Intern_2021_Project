package com.i2iproject.requestresponderimps.getresponders;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.builders.CollectionBuilder;
import com.i2iproject.builders.TemplateBuilder;
import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.builders.models.TemplateData;
import com.i2iproject.requestresponders.GetRequestResponder;

@Component("GetForgotPasswordResponder")
@Scope("prototype")
public class ForgotPasswordResponderImp implements GetRequestResponder{
	private CollectionBuilder collectionBuilder;
	private TemplateBuilder templateBuilder;

	public ForgotPasswordResponderImp(CollectionBuilder collectionBuilder,TemplateBuilder templateBuilder) {
		this.collectionBuilder = collectionBuilder;
		this.templateBuilder = templateBuilder;
	}
	
	@Override
	public CollectionResponse respond() {
		collectionBuilder.addHref("/api/login/forgot-password");
		collectionBuilder.addVersion("3.12.0");
		addTemplate();
		return collectionBuilder.getConstructedCollection();
	}

	private void addTemplate() {
		addEmailToTemplate();
		collectionBuilder.addTemplate(templateBuilder.getConstructedTemplate());
	}

	private void addEmailToTemplate() {
		final String name = "email";
		templateBuilder.addTemplateData(new TemplateData(name));
	}
}
