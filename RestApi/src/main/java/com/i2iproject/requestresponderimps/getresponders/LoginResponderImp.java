package com.i2iproject.requestresponderimps.getresponders;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.builders.CollectionBuilder;
import com.i2iproject.builders.LinksBuilder;
import com.i2iproject.builders.TemplateBuilder;
import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.builders.models.LinkRelation;
import com.i2iproject.builders.models.TemplateData;
import com.i2iproject.requestresponders.GetRequestResponder;

@Component("GetLoginResponder")
@Scope("prototype")
public class LoginResponderImp implements GetRequestResponder{
	private CollectionBuilder collectionBuilder;
	private TemplateBuilder templateBuilder;
	private LinksBuilder linksBuilder;
	
	public LoginResponderImp(CollectionBuilder collectionBuilder,TemplateBuilder templateBuilder,LinksBuilder linksBuilder) {
		this.collectionBuilder = collectionBuilder;
		this.templateBuilder = templateBuilder;
		this.linksBuilder = linksBuilder;
	}
	
	@Override
	public CollectionResponse respond() {
		collectionBuilder.addHref("/api/login");
		collectionBuilder.addVersion("3.12.0");
		addLinks();
		addTemplate();
		return collectionBuilder.getConstructedCollection();
	}

	private void addLinks() {
		addForgotPasswordRelation();
		collectionBuilder.addLinks(linksBuilder.getConstructedLinks());
	}
	
	private void addForgotPasswordRelation() {
		final String rel= "forgotPassword";
		final String href= "/api/login/forgot-password";
		linksBuilder.addLinkRelation(new LinkRelation(rel, href));	
	}

	private void addTemplate() {
		addPhoneDataToTemplate();
		addPasswordDataToTemplate();
		collectionBuilder.addTemplate(templateBuilder.getConstructedTemplate());
	}
	
	private void addPhoneDataToTemplate() {
		final String name = "phoneNumber";
		templateBuilder.addTemplateData(new TemplateData(name));
	}
	
	private void addPasswordDataToTemplate() {
		final String name = "password";
		templateBuilder.addTemplateData(new TemplateData(name));
	}

}
