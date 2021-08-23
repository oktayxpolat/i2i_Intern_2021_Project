package com.i2iproject.builders;

import com.i2iproject.builders.models.LinkRelation;
import com.i2iproject.builders.models.Links;

public interface LinksBuilder {
	public void addLinkRelation(LinkRelation relationToAdd);
	public Links getConstructedLinks();
}
