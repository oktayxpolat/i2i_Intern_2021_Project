package com.i2iproject.responders;

import com.i2iproject.responders.models.Register;
import com.i2iproject.responders.models.RegisterResponse;

public interface RegisterResponder {
	public RegisterResponse respond(Register requestBody);
}
