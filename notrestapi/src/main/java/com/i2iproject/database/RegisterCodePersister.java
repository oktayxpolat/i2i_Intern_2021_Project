package com.i2iproject.database;

import com.i2iproject.database.models.RegisterConfirmationId;
import com.i2iproject.responders.models.Register;

public interface RegisterCodePersister {
	public final int persistingRegisterCodeFails = -1;
	public RegisterConfirmationId persistRegisterRequest(Register registerRequestInfo);
}
