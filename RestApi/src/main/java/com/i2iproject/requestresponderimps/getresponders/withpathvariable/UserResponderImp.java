package com.i2iproject.requestresponderimps.getresponders.withpathvariable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.builders.CollectionBuilder;
import com.i2iproject.builders.ItemsBuilder;
import com.i2iproject.builders.LinksBuilder;
import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.builders.models.Item;
import com.i2iproject.builders.models.ItemsData;
import com.i2iproject.builders.models.LinkRelation;
import com.i2iproject.requestresponders.GetRequestWithPathVariableResponder;

@Component("GetUserResponder")
@Scope("prototype")
public class UserResponderImp implements GetRequestWithPathVariableResponder{
	private CollectionBuilder collectionBuilder;
	private ItemsBuilder itemsBuilder;
	private LinksBuilder linksBuilder;
	private Item newItem;
	
	public UserResponderImp(CollectionBuilder collectionBuilder, 
			ItemsBuilder itemsBuilder, 
			LinksBuilder linksBuilder) {
		this.collectionBuilder = collectionBuilder;
		this.itemsBuilder = itemsBuilder;
		this.linksBuilder = linksBuilder;
	}

	@Override
	public CollectionResponse respond(Object pathVariables) {
		collectionBuilder.addHref("/api/users/5");
		collectionBuilder.addVersion("3.12.0");
		addItems();
		addLinks();
		return collectionBuilder.getConstructedCollection();
	}

	private void addItems() {
		addItem();
		collectionBuilder.addItems(itemsBuilder.getConstructedItems());
	}

	private void addItem() {
		newItem = new Item();
		addNameToItems();
		addLastNameToItems();
		addPhoneNumberToItems();
		addEmailToItems();
		itemsBuilder.addItem(newItem);
	}

	private void addNameToItems() {
		newItem = new Item();
		final String name= "name";
		final String value= "bakana";
		newItem.addData(new ItemsData(name,value));
	}
	
	private void addLastNameToItems() {
		newItem = new Item();
		final String name= "lastName";
		final String value= "AHMET";
		newItem.addData(new ItemsData(name,value));
	}
	
	private void addPhoneNumberToItems() {
		newItem = new Item();
		final String name= "phoneNumber";
		final String value= "johndoe@gmail.com";
		newItem.addData(new ItemsData(name,value));
	}

	private void addEmailToItems() {
		newItem = new Item();
		final String name= "email";
		final String value= "johndoe@gmail.com";
		newItem.addData(new ItemsData(name,value));
	}

	private void addLinks() {
		addPackageOwned();
		addChangeEmail();
		addChangePassword();
		collectionBuilder.addLinks(linksBuilder.getConstructedLinks());
	}

	private void addPackageOwned() {
		final String rel= "packageOwned";
		final String href= "/api/users/5/package";
		linksBuilder.addLinkRelation(new LinkRelation(rel, href));
	}

	private void addChangeEmail() {
		final String rel= "changeEmail";
		final String href= "/api/users/5/change-account-email";
		linksBuilder.addLinkRelation(new LinkRelation(rel, href));
	}

	private void addChangePassword() {
		final String rel= "changePassword";
		final String href= "/api/users/5/change-account-password";
		linksBuilder.addLinkRelation(new LinkRelation(rel, href));
	}
	
}
