package com.i2iproject.databaseimps;

import org.springframework.stereotype.Component;

import com.i2iproject.database.UserLoader;
import com.i2iproject.database.models.UserModel;

@Component
public class UserLoaderImp implements UserLoader{

	@Override
	public UserModel loadUserByPhoneNumber(String phoneNumber) {
		final String phoneNum = "5347772945";
		final String password = "password.534945.password";
		UserModel user = new UserModel();
		user.setMsidn(phoneNum);
		user.setPassWord(password);
		return user;
	}

}
