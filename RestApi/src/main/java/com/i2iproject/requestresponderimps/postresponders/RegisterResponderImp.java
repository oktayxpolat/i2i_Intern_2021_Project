package com.i2iproject.requestresponderimps.postresponders;

import java.util.Arrays;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.apiexceptions.BadRequestException;
import com.i2iproject.builders.CollectionBuilder;
//import com.i2iproject.builders.ItemsBuilder;
import com.i2iproject.builders.TemplateBuilder;
import com.i2iproject.builders.models.CollectionResponse;
//import com.i2iproject.builders.models.Item;
//import com.i2iproject.builders.models.ItemsData;
import com.i2iproject.builders.models.TemplateData;
import com.i2iproject.requestresponderimps.postresponders.utils.RequestBodyVerifier;
import com.i2iproject.requestresponders.PostRequestResponder;
import com.i2iproject.requestresponders.models.PostRequestBody;

@Component("PostRegisterResponder")
@Scope("prototype")
public class RegisterResponderImp implements PostRequestResponder{
	private CollectionBuilder collectionBuilder;
	//private ItemsBuilder itemsBuilder;
	private TemplateBuilder templateBuilder;
	private RequestBodyVerifier bodyVerifier;
	//private Item newItem;
	
	public RegisterResponderImp(CollectionBuilder collectionBuilder, 
			//ItemsBuilder itemsBuilder, 
			TemplateBuilder templateBuilder,
			RequestBodyVerifier bodyVerifier) {
		this.collectionBuilder = collectionBuilder;
		//this.itemsBuilder = itemsBuilder;
		this.templateBuilder = templateBuilder;
		this.bodyVerifier = bodyVerifier;
	}

	@Override
	public CollectionResponse respond(PostRequestBody requestBody) {
		checkRequestBody(requestBody);
		return produceResponse();
	}
	
	private void checkRequestBody(PostRequestBody requestBody) {
		String[] namesWhichAreSupposedToBeIncludedInTheBody = {"name", "lastName", "email" , "phoneNumber", "password"};
		boolean isBodyCorrect = bodyVerifier.
				isRequstBodyContainsCorrectNames(requestBody, namesWhichAreSupposedToBeIncludedInTheBody);
		if(!isBodyCorrect)
			throw new BadRequestException("REQUEST BODY MUST CONTAIN:" + 
					Arrays.toString(namesWhichAreSupposedToBeIncludedInTheBody) + 
					" AND THE VALUE MUST BE PROVIDED FOR THOSE ATTIBUTES"
					);
	}
	
	private CollectionResponse produceResponse() {
		collectionBuilder.addHref("/api/register/10000/confirm");
		collectionBuilder.addVersion("3.12.0");
		//addItems();
		addTemplate();
		return collectionBuilder.getConstructedCollection();
	}

	/*
	private void addItems() {
		addItem();
		collectionBuilder.addItems(itemsBuilder.getConstructedItems());
	}

	private void addItem() {
		newItem = new Item();
		addCodeValidityPeriod();
		itemsBuilder.addItem(newItem);
	}

	private void addCodeValidityPeriod() {
		final String name = "codeValidityPeriod";
		final String value = "3";
		newItem.addData(new ItemsData(name,value));
	}*/

	private void addTemplate() {
		addCodeReceivedViaEmail();
		collectionBuilder.addTemplate(templateBuilder.getConstructedTemplate());
	}

	private void addCodeReceivedViaEmail() {
		final String name = "codeReceivedViaEmail";
		templateBuilder.addTemplateData(new TemplateData(name));
	}
	

}
