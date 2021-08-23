package com.i2iproject.builderImps;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.builders.CollectionBuilder;
import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.builders.models.Item;
import com.i2iproject.builders.models.Links;
import com.i2iproject.builders.models.Template;

@Component
@Scope("prototype")
public class CollectionBuilderImp implements CollectionBuilder{
	private CollectionResponse collection;
	
	public CollectionBuilderImp(CollectionResponse collection) {
		this.collection = collection;
	}
	
	@Override
	public void addHref(String href) {
		collection.setHref(href);
	}

	@Override
	public void addVersion(String version) {
		collection.setVersion(version);
	}

	@Override
	public void addItems(List<Item> itemsToAdd) {
		collection.setItems(itemsToAdd);
	}

	@Override
	public void addLinks(Links linksToAdd) {
		collection.setLinks(linksToAdd.getLinks());
	}

	@Override
	public void addTemplate(Template templateToAdd) {
		collection.setTemplate(templateToAdd);
	}

	@Override
	public CollectionResponse getConstructedCollection() {
		return collection;
	}
}
