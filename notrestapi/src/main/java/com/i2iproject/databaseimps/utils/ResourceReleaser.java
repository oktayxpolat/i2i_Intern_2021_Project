package com.i2iproject.databaseimps.utils;

import java.sql.CallableStatement;
import java.sql.Connection;

public interface ResourceReleaser {
	public void relaseConnection(Connection connectionToRelase);
	public void relaseCallableStatement(CallableStatement releaseCallableStatement);
}
