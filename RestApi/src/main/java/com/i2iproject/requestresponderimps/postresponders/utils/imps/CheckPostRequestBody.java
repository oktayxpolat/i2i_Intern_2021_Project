package com.i2iproject.requestresponderimps.postresponders.utils.imps;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.requestresponderimps.postresponders.utils.RequestBodyVerifier;
import com.i2iproject.requestresponders.models.DataElement;
import com.i2iproject.requestresponders.models.PostRequestBody;

@Component
@Scope("prototype")
public class CheckPostRequestBody implements RequestBodyVerifier{
	private PostRequestBody requestBodyToCheck;
	private Set<String> namesWhichRequestBodyMustInclude;
	private String[] namesWhichRequestBodyMustIncludeAsArray;
	private int numberOfMatchedNames;
	
	public boolean isRequstBodyContainsCorrectNames(PostRequestBody requestBodyToCheck,
			String[] namesWhichRequestBodyMustIncludeAsArray) {
		this.namesWhichRequestBodyMustIncludeAsArray = namesWhichRequestBodyMustIncludeAsArray;
		this.requestBodyToCheck = requestBodyToCheck;
		createSetRepresentationForFastChecking();
		return isContainsCorrectNames();
	}
	
	private void createSetRepresentationForFastChecking() {
		this.namesWhichRequestBodyMustInclude = new HashSet<String>(Arrays.asList(namesWhichRequestBodyMustIncludeAsArray));
	}
	
	private boolean isContainsCorrectNames() {
		List<DataElement> datasInTemplateInRequestBody = requestBodyToCheck.getTemplate().getData();
		for(DataElement currentData : datasInTemplateInRequestBody) {
			if(!isCurrentDataCorrect(currentData))
				return false;
			numberOfMatchedNames++;
		}
		return isNumberOfNamesFoundEqualsToNumberWhichIsSupposedToBeFound();
	}
	
	private boolean isCurrentDataCorrect(DataElement currentData) {
		return isCurrentNameContainedInTheNamesWhichAreSupposedToBeFound(currentData.getName()) 
				&& isValueProvidedForTheCurrentName(currentData.getValue());
	}
	
	private boolean isCurrentNameContainedInTheNamesWhichAreSupposedToBeFound(String name) {
		return namesWhichRequestBodyMustInclude.contains(name);
	}
	
	private boolean isValueProvidedForTheCurrentName(String value) {
		return value != null;
	}
	
	private boolean isNumberOfNamesFoundEqualsToNumberWhichIsSupposedToBeFound() {
		return namesWhichRequestBodyMustInclude.size() == numberOfMatchedNames;
	}
}
