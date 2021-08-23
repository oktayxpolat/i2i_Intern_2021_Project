package com.i2iproject.responders;

public interface ForgotPasswordResponder {
	public boolean sendPasswordChangeCodeToGivenEmail(String email);
}
