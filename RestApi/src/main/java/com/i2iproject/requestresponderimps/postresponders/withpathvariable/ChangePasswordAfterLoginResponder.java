package com.i2iproject.requestresponderimps.postresponders.withpathvariable;

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
import com.i2iproject.requestresponders.PostRequestWithPathVariableResponder;
import com.i2iproject.requestresponders.models.PostRequestBody;

@Component("PostChangePasswordAfterLoginResponder")
@Scope("prototype")
public class ChangePasswordAfterLoginResponder implements PostRequestWithPathVariableResponder{
	private CollectionBuilder collectionBuilder;
	//private ItemsBuilder itemsBuilder;
	private TemplateBuilder templateBuilder;
	private RequestBodyVerifier bodyVerifier;
	//private Item newItem;
	
	public ChangePasswordAfterLoginResponder(CollectionBuilder collectionBuilder, 
			//ItemsBuilder itemsBuilder, 
			TemplateBuilder templateBuilder,
			RequestBodyVerifier bodyVerifier) {
		this.collectionBuilder = collectionBuilder;
		//this.itemsBuilder = itemsBuilder;
		this.templateBuilder = templateBuilder;
		this.bodyVerifier = bodyVerifier;
	}

	@Override
	public CollectionResponse respond(PostRequestBody requestBody, Object pathVariables) {
		//use code generator to generate code
		//then use userID in pathVariables and email in requestBody and genearated code
		//to persist the code via passwordChangeCodePersister
		checkRequestBody(requestBody);
		return produceResponse();
	}
	
	private void checkRequestBody(PostRequestBody requestBody) {
		String[] namesWhichAreSupposedToBeIncludedInTheBody = {"email"};
		boolean isBodyCorrect = bodyVerifier.
				isRequstBodyContainsCorrectNames(requestBody, namesWhichAreSupposedToBeIncludedInTheBody);
		if(!isBodyCorrect)
			throw new BadRequestException("REQUEST BODY MUST CONTAIN:" + 
					Arrays.toString(namesWhichAreSupposedToBeIncludedInTheBody) + 
					" AND THE VALUE MUST BE PROVIDED FOR THOSE ATTIBUTES"
					);
	}
	
	private CollectionResponse produceResponse() {
		collectionBuilder.addHref("/api/users/5/change-account-password/confirm");
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
	}
	*/
	
	private void addTemplate() {
		addCodeReceivedViaEmail();
		addNewEmailToTemplate();
		collectionBuilder.addTemplate(templateBuilder.getConstructedTemplate());
	}

	private void addCodeReceivedViaEmail() {
		final String name = "codeReceivedViaEmail";
		templateBuilder.addTemplateData(new TemplateData(name));
	}


	private void addNewEmailToTemplate() {
		final String name = "newPassword";
		templateBuilder.addTemplateData(new TemplateData(name));
	}

}
