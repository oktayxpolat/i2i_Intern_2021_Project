package com.i2iproject.database.models;

import java.util.Date;

public class FullPackageInfo extends DatabaseHalfOfPackageInfo{
	private VoltDBHalfOfThePackageInfo voltDBHalf;
	
	public FullPackageInfo() {
		voltDBHalf = new VoltDBHalfOfThePackageInfo();
	}
	
	public void addDatabaseHalfOfPackageInfo(DatabaseHalfOfPackageInfo databaseHalf) {
		this.setMsisdn(databaseHalf.getMsisdn());
		this.setPackageId(databaseHalf.getPackageId()); 
		this.setPackageLimit(databaseHalf.getPackageLimit());
		this.setPackageName(databaseHalf.getPackageName());
		this.setPackageType(databaseHalf.getPackageType());
		this.setVisibility(databaseHalf.getVisibility());
	}
	
	public void addVoltDBHalfOfPackageInfo(VoltDBHalfOfThePackageInfo voltDBHalf) {
		this.voltDBHalf = voltDBHalf;
	}
	
	public int getUsedAmount() {
		return voltDBHalf.getUsedAmount();
	}
	public void setUsedAmount(int usedAmount) {
		voltDBHalf.setUsedAmount(usedAmount);
	}
	public Date getStartDate() {
		return voltDBHalf.getStartDate();
	}
	public void setStartDate(Date startDate) {
		voltDBHalf.setStartDate(startDate);;
	}
	public Date getEndDate() {
		return voltDBHalf.getEndDate();
	}
	public void setEndDate(Date endDate) {
		voltDBHalf.setEndDate(endDate);
	}
}
