package com.i2iproject.database;

import com.i2iproject.database.models.PasswordChangeAfterLoginConfirmationInfo;

public interface ConfirmerOfPasswordChangeAfterLogin {
	public boolean confirmPasswordChangeAfterLogin(PasswordChangeAfterLoginConfirmationInfo confirmationRequestInfo);
}
