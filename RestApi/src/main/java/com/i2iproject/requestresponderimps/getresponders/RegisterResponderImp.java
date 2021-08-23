package com.i2iproject.requestresponderimps.getresponders;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.builders.CollectionBuilder;
import com.i2iproject.builders.TemplateBuilder;
import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.builders.models.TemplateData;
import com.i2iproject.requestresponders.GetRequestResponder;

@Component("GetRegisterResponder")
@Scope("prototype")
public class RegisterResponderImp implements GetRequestResponder{
	private CollectionBuilder collectionBuilder;
	private TemplateBuilder templateBuilder;
	
	public RegisterResponderImp(CollectionBuilder collectionBuilder, TemplateBuilder templateBuilder) {
		this.collectionBuilder = collectionBuilder;
		this.templateBuilder = templateBuilder;
	}
	
	@Override
	public CollectionResponse respond() {
		collectionBuilder.addHref("/api/register");
		collectionBuilder.addVersion("3.12.0");
		addTemplate();
		return collectionBuilder.getConstructedCollection();
	}

	private void addTemplate() {
		addNameToTemplate();
		addLastNameToTemplate();
		addEmailToTemplate();
		addPhoneNumberToTemplate();
		addPasswordDataToTemplate();
		collectionBuilder.addTemplate(templateBuilder.getConstructedTemplate());
	}

	private void addNameToTemplate() {
		final String name = "name";
		templateBuilder.addTemplateData(new TemplateData(name));
	}

	private void addLastNameToTemplate() {
		final String name = "lastName";
		templateBuilder.addTemplateData(new TemplateData(name));
	}

	private void addEmailToTemplate() {
		final String name = "email";
		templateBuilder.addTemplateData(new TemplateData(name));
	}
	
	private void addPhoneNumberToTemplate() {
		final String name = "phoneNumber";
		templateBuilder.addTemplateData(new TemplateData(name));
	}

	private void addPasswordDataToTemplate() {
		final String name = "password";
		templateBuilder.addTemplateData(new TemplateData(name));
	}

}
