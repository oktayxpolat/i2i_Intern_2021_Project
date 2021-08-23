package com.i2iproject.requestresponders;

import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.requestresponders.models.PostRequestBody;

public interface PostRequestResponder {
	public CollectionResponse respond(PostRequestBody requestBody); //parametre almalıdır.
}
