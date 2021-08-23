package com.i2iproject.database.models;

public class DatabaseHalfOfPackageInfo {
	private int userId;
	private int packageId;
	private String msisdn;
	private String packageType;
	private String packageName;
	private int packageLimit;
	private String businessZone;
	private boolean visibility;
	
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public int getPackageLimit() {
		return packageLimit;
	}
	public void setPackageLimit(int packageLimit) {
		this.packageLimit = packageLimit;
	}
	
	public String getBusinessZone() {
		return businessZone;
	}
	public void setBusinessZone(String businessZone) {
		this.businessZone = businessZone;
	}
	
	public boolean getVisibility() {
		return visibility;
	}
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

}
