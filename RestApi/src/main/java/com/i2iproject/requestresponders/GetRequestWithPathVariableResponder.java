package com.i2iproject.requestresponders;

import com.i2iproject.builders.models.CollectionResponse;

public interface GetRequestWithPathVariableResponder {
	public CollectionResponse respond(Object pathVariables);
}
