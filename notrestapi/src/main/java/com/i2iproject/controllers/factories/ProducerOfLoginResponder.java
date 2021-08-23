package com.i2iproject.controllers.factories;

import com.i2iproject.responders.LoginResponder;

public interface ProducerOfLoginResponder {
	public LoginResponder produceLoginResponder();
}
