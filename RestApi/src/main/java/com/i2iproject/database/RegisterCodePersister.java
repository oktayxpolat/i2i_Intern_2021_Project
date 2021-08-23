package com.i2iproject.database;

import com.i2iproject.database.models.RegisterConfirmationId;
import com.i2iproject.database.models.RegisterRequestInfo;

public interface RegisterCodePersister {
	public RegisterConfirmationId persistRegisterCode(RegisterRequestInfo registerRequestInfo);
}
