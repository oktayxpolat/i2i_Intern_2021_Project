package com.i2iproject.database;

import com.i2iproject.database.models.ForgotPasswordConfirmationInfo;

public interface ConfirmerOfPasswordChange {
	public boolean confirmPasswordChange(ForgotPasswordConfirmationInfo passwordChangeConfirmationInfo);
}
