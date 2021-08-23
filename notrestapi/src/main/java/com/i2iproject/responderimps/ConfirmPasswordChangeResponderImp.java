package com.i2iproject.responderimps;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.ConfirmerOfPasswordChange;
import com.i2iproject.responders.ConfirmPasswordChangeResponder;
import com.i2iproject.responders.models.ForgotPasswordConfirm;

@Component
@Scope("prototype")
public class ConfirmPasswordChangeResponderImp implements ConfirmPasswordChangeResponder{
	private ConfirmerOfPasswordChange passwordChangeConfirmer;

	public ConfirmPasswordChangeResponderImp(ConfirmerOfPasswordChange passwordChangeConfirmer) {
		this.passwordChangeConfirmer = passwordChangeConfirmer;
	}

	@Override
	public boolean confirmPasswordChange(ForgotPasswordConfirm confirmRequestInfo) {
		return passwordChangeConfirmer.confirmPasswordChange(confirmRequestInfo);
	}
	
}
