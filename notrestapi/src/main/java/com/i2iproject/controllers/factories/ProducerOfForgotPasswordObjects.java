package com.i2iproject.controllers.factories;

import com.i2iproject.responders.ConfirmPasswordChangeResponder;
import com.i2iproject.responders.ForgotPasswordResponder;

public interface ProducerOfForgotPasswordObjects {
	public ForgotPasswordResponder produceForgotPasswordResponder();
	public ConfirmPasswordChangeResponder produceConfirmerOfForgotPasswordResponder();
}
