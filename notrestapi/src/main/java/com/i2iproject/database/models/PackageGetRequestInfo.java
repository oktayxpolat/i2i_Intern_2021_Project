package com.i2iproject.database.models;

public class PackageGetRequestInfo {
	private int userId;

	public PackageGetRequestInfo(int userId) {
		this.userId = userId;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
