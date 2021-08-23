package com.i2iproject.requestresponderimps.postresponders;

import java.util.Arrays;

import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.i2iproject.apiexceptions.BadRequestException;
import com.i2iproject.apiexceptions.UnAuthorizedException;
import com.i2iproject.builders.CollectionBuilder;
import com.i2iproject.builders.ItemsBuilder;
import com.i2iproject.builders.LinksBuilder;
import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.builders.models.Item;
import com.i2iproject.builders.models.ItemsData;
import com.i2iproject.builders.models.LinkRelation;
import com.i2iproject.requestresponderimps.postresponders.utils.RequestBodyVerifier;
import com.i2iproject.requestresponders.PostRequestResponder;
import com.i2iproject.requestresponders.models.DataElement;
import com.i2iproject.requestresponders.models.PostRequestBody;
import com.i2iproject.security.MyUserDetailsService;
import com.i2iproject.security.utils.JwtUtil;

@Component("PostLoginResponder")
@Scope("prototype")
public class LoginResponderImp implements PostRequestResponder{
	private CollectionBuilder collectionBuilder;
	private ItemsBuilder itemsBuilder;
	private LinksBuilder linksBuilder;
	private RequestBodyVerifier bodyVerifier;
	private AuthenticationManager authenticationManager;
	private MyUserDetailsService userDetailsService;
	private JwtUtil jwtTokenUtil;
	private String producedJwt;
	private Item newItem;
	
	public LoginResponderImp(CollectionBuilder collectionBuilder, ItemsBuilder itemsBuilder, LinksBuilder linksBuilder,
			RequestBodyVerifier bodyVerifier, AuthenticationManager authenticationManager,
			MyUserDetailsService userDetailsService, JwtUtil jwtTokenUtil) {
		this.collectionBuilder = collectionBuilder;
		this.itemsBuilder = itemsBuilder;
		this.linksBuilder = linksBuilder;
		this.bodyVerifier = bodyVerifier;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	public CollectionResponse respond(PostRequestBody requestBody) {
		checkRequestBody(requestBody);
		authenticate(requestBody);
		return produceResponse();
	}
	
	private void authenticate(PostRequestBody requestBody) {
		String name = extractName(requestBody);
		String password = extractPassword(requestBody);
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(name, password));
		}catch (BadCredentialsException e) {
			throw new UnAuthorizedException("BAD CREDENTIALS");
		}catch (AuthenticationException e) {
			throw new UnAuthorizedException("BAD CREDENTIALS");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(name);
		producedJwt = jwtTokenUtil.generateToken(userDetails);
	}

	private String extractName(PostRequestBody requestBody) { //can not return null because request body is checked
		 String name=null; 
		 for(DataElement currentDataElement : requestBody.getTemplate().getData()) {
			 if(currentDataElement.getName().equals("phoneNumber"))
				 name = currentDataElement.getValue();
		 }
		 return name;
	}
	
	private String extractPassword(PostRequestBody requestBody) {
		 String password=null; 
		 for(DataElement currentDataElement : requestBody.getTemplate().getData()) {
			 if(currentDataElement.getName().equals("password"))
				 password = currentDataElement.getValue();
		 }
		 return password;
	}

	private void checkRequestBody(PostRequestBody requestBody) {
		String[] namesWhichAreSupposedToBeIncludedInTheBody = {"phoneNumber", "password"};
		boolean isBodyCorrect = bodyVerifier.
				isRequstBodyContainsCorrectNames(requestBody, namesWhichAreSupposedToBeIncludedInTheBody);
		if(!isBodyCorrect)
			throw new BadRequestException("REQUEST BODY MUST CONTAIN:" + 
					Arrays.toString(namesWhichAreSupposedToBeIncludedInTheBody) + 
					" AND THE VALUE MUST BE PROVIDED FOR THOSE ATTIBUTES"
					);
	}
	
	private CollectionResponse produceResponse() {
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
		addJwtToItems();
		itemsBuilder.addItem(newItem);
	}

	private void addNameToItems() {
		final String name= "name";
		final String value= "bakana";
		newItem.addData(new ItemsData(name,value));
	}
	
	private void addLastNameToItems() {
		final String name= "lastName";
		final String value= "AHMET";
		newItem.addData(new ItemsData(name,value));
	}
	
	private void addPhoneNumberToItems() {
		final String name= "phoneNumber";
		final String value= "johndoe@gmail.com";
		newItem.addData(new ItemsData(name,value));	
	}
	
	private void addEmailToItems() {
		final String name= "email";
		final String value= "johndoe@gmail.com";
		newItem.addData(new ItemsData(name,value));
	}
	
	private void addJwtToItems() {
		final String name= "jwt";
		newItem.addData(new ItemsData(name,producedJwt));
	}

	private void addLinks() {
		addPackageOwned();
		//addChangeEmail();
		addChangePassword();
		collectionBuilder.addLinks(linksBuilder.getConstructedLinks());
	}

	private void addPackageOwned() {
		final String rel= "packageOwned";
		final String href= "/api/users/5/package";
		linksBuilder.addLinkRelation(new LinkRelation(rel, href));
	}

	/*
	private void addChangeEmail() {
		final String rel= "changeEmail";
		final String href= "/api/users/5/change-account-email";
		linksBuilder.addLinkRelation(new LinkRelation(rel, href));
	}*/
	
	private void addChangePassword() {
		final String rel= "changePassword";
		final String href= "/api/users/5/change-account-password";
		linksBuilder.addLinkRelation(new LinkRelation(rel, href));
	}

}
