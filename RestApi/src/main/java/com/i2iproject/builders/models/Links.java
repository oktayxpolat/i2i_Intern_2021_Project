package com.i2iproject.builders.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Links {
	private List<LinkRelation> links;

	public Links() {
		links = new ArrayList<>();
	}
	
	public void addLink(LinkRelation dataToBeAdded) {
		links.add(dataToBeAdded);
	}

	public List<LinkRelation> getLinks() {
		return links;
	}
	
}
