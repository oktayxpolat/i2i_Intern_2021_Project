package com.i2iproject.database;

import com.i2iproject.database.models.ForgotPasswordPersistenceInfo;

public interface PasswordChangeCodePersister {
	public boolean persistPasswordChangeCode(ForgotPasswordPersistenceInfo passwordChangePersistenceInfo);
}
