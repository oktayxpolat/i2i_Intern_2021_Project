package com.i2iproject.responders;

import com.i2iproject.responders.models.LoginResponderModel;
import com.i2iproject.responders.models.LoginResponse;

public interface LoginResponder {
	public LoginResponse respond(LoginResponderModel loginResponderInfo);
}
