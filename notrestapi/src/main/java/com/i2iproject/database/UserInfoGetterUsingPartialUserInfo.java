package com.i2iproject.database;

import com.i2iproject.database.models.FullUserInfo;
import com.i2iproject.database.models.PartialUserInfo;

public interface UserInfoGetterUsingPartialUserInfo {
	FullUserInfo getFullUserInfoFromPartialInfo(PartialUserInfo partialUserInfo);
}
