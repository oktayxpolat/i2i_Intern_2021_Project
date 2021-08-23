package com.i2iproject.builders;

import java.util.List;

import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.builders.models.Item;
import com.i2iproject.builders.models.Links;
import com.i2iproject.builders.models.Template;

public interface CollectionBuilder {
	public void addHref(String href);
	public void addVersion(String version);
	public void addItems(List<Item> itemsToAdd);
	public void addLinks(Links linksToAdd);
	public void addTemplate(Template templateToAdd);
	public CollectionResponse getConstructedCollection();
}
