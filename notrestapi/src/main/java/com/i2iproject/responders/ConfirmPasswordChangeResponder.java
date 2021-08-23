package com.i2iproject.responders;

import com.i2iproject.responders.models.ForgotPasswordConfirm;

public interface ConfirmPasswordChangeResponder {
	public boolean confirmPasswordChange(ForgotPasswordConfirm confirmRequestInfo);
}
