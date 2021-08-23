package com.i2iproject.database;

import com.i2iproject.database.models.UserModel;

public interface UserLoader {
	public UserModel loadUserByPhoneNumber(String msidn);
}
