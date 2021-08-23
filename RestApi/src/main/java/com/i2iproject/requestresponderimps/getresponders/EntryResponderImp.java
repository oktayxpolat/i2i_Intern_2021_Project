package com.i2iproject.requestresponderimps.getresponders;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.builders.CollectionBuilder;
import com.i2iproject.builders.LinksBuilder;
import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.builders.models.LinkRelation;
import com.i2iproject.requestresponders.GetRequestResponder;

@Component("GetEntryResponder")
@Scope("prototype")
public class EntryResponderImp implements GetRequestResponder{
	private CollectionBuilder collectionBuilder;
	private LinksBuilder linksBuilder;
	
	public EntryResponderImp(CollectionBuilder collectionBuilder,LinksBuilder linksBuilder) {
		this.collectionBuilder = collectionBuilder;
		this.linksBuilder = linksBuilder;
	}

	@Override
	public CollectionResponse respond() {
		collectionBuilder.addHref("/api");
		collectionBuilder.addVersion("3.12.0");
		addLinks();
		return collectionBuilder.getConstructedCollection();
	}
	
	private void addLinks() {
		addLoginRelation();
		addRegisterRelation();
		collectionBuilder.addLinks(linksBuilder.getConstructedLinks());
	}
	
	private void addLoginRelation() {
		final String rel= "login";
		final String href= "/api/login";
		linksBuilder.addLinkRelation(new LinkRelation(rel, href));
	}
	
	private void addRegisterRelation() {
		final String rel= "register";
		final String href= "/api/register";	
		linksBuilder.addLinkRelation(new LinkRelation(rel, href));
	}

}
