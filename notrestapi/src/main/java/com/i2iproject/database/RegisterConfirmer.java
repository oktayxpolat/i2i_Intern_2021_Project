package com.i2iproject.database;

import com.i2iproject.responders.models.ConfirmRegister;

public interface RegisterConfirmer {
	public boolean confirmRegister(ConfirmRegister registerConfirmationInfo);
}
