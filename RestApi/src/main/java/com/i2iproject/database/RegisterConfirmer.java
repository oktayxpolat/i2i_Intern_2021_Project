package com.i2iproject.database;

import com.i2iproject.database.models.RegisterConfirmationInfo;

public interface RegisterConfirmer {
	public boolean confirmRegister(RegisterConfirmationInfo registerConfirmationInfo);
}
