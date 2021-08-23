package com.i2iproject.database;

import com.i2iproject.responders.models.ForgotPasswordConfirm;

public interface ConfirmerOfPasswordChange {
	public boolean confirmPasswordChange(ForgotPasswordConfirm passwordChangeConfirmationInfo);
}
