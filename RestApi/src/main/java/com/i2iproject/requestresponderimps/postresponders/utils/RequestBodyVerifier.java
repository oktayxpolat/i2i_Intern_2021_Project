package com.i2iproject.requestresponderimps.postresponders.utils;

import com.i2iproject.requestresponders.models.PostRequestBody;

public interface RequestBodyVerifier {
	public boolean isRequstBodyContainsCorrectNames(PostRequestBody requestBodyToCheck,
			String[] namesWhichRequestBodyMustIncludeAsArray);
}
