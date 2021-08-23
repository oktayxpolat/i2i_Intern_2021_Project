package com.i2iproject.responders.utils;

public interface EmailSender {
	public void sendEmailWithText(
			String toEmail,
			String body,
			String subject
			);
}
