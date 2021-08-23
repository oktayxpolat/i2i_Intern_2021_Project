package com.i2iproject.requestresponders;

import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.requestresponders.models.PostRequestBody;

public interface PostRequestWithPathVariableResponder {
	public CollectionResponse respond(PostRequestBody requestBody, Object pathVariables); 
}
