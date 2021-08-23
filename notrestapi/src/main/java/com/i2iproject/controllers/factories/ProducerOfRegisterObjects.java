package com.i2iproject.controllers.factories;

import com.i2iproject.responders.ConfirmRegisterRequestResponder;
import com.i2iproject.responders.RegisterResponder;

public interface ProducerOfRegisterObjects {
	public RegisterResponder produceRegisterResponder();
	public ConfirmRegisterRequestResponder produceRegisterConfirmer();
}
