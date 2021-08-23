package com.i2iproject.database;

import com.i2iproject.database.models.FullUserInfo;

public interface UserInfoGetterById {
	FullUserInfo getUserInfoById(int userId);
}
