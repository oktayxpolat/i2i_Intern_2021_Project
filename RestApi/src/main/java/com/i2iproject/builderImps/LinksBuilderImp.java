package com.i2iproject.builderImps;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.builders.LinksBuilder;
import com.i2iproject.builders.models.LinkRelation;
import com.i2iproject.builders.models.Links;

@Component
@Scope("prototype")
public class LinksBuilderImp implements LinksBuilder{
	private Links buildedLinks;
	
	public LinksBuilderImp(Links buildedLinks) {
		this.buildedLinks = buildedLinks;
	}
	
	@Override
	public void addLinkRelation(LinkRelation relationToAdd) {
		buildedLinks.addLink(relationToAdd);
	}

	@Override
	public Links getConstructedLinks() {
		return buildedLinks;
	}
	
}
