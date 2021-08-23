package com.i2iproject.database.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FullPackageInfo{
	private int userId;
	private String msisdn;
	private int packageId;
	private int usedAmount;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String packageType;
	private String packageName;
	private int packageLimit;
	private String businessZone;
	private boolean visible;
	
	public static FullPackageInfo combineDatabaseHalfAndVoltDBHalf(DatabaseHalfOfPackageInfo databaseHalf, VoltDBHalfOfThePackageInfo voltDBHalf) {
		FullPackageInfo combinedFullPackageInfo = new FullPackageInfo();
		combinedFullPackageInfo.setUserId(databaseHalf.getUserId());
		combinedFullPackageInfo.setPackageId(databaseHalf.getPackageId());
		combinedFullPackageInfo.setMsisdn(databaseHalf.getMsisdn());
		combinedFullPackageInfo.setUsedAmount(voltDBHalf.getUsedAmount());
		combinedFullPackageInfo.setStartDate(voltDBHalf.getStartDate());
		combinedFullPackageInfo.setEndDate(voltDBHalf.getEndDate());
		combinedFullPackageInfo.setPackageType(databaseHalf.getPackageType());
		combinedFullPackageInfo.setPackageName(databaseHalf.getPackageName());
		combinedFullPackageInfo.setPackageLimit(databaseHalf.getPackageLimit());
		combinedFullPackageInfo.setBusinessZone(databaseHalf.getBusinessZone());
		combinedFullPackageInfo.setVisible(databaseHalf.getVisibility());
		return combinedFullPackageInfo;
	}
	
	public static List<DatabaseHalfOfPackageInfo> extractDatabaseHalfListFromFullPackageInfoList(
			List<FullPackageInfo> fullPackageInfoList){
		List<DatabaseHalfOfPackageInfo> databaseHalfList = new ArrayList<>();
		for(FullPackageInfo currentFullPackageInfo : fullPackageInfoList) {
			databaseHalfList.add(
					extractDatabaseHalfFromFullPackageInfo(currentFullPackageInfo)
			);
		}
		return databaseHalfList;
	}
	
	private static DatabaseHalfOfPackageInfo extractDatabaseHalfFromFullPackageInfo(
			FullPackageInfo fullPackageInfo) {
		DatabaseHalfOfPackageInfo databaseHalf = new DatabaseHalfOfPackageInfo();
		databaseHalf.setBusinessZone(fullPackageInfo.getBusinessZone());
		databaseHalf.setMsisdn(fullPackageInfo.getMsisdn());
		databaseHalf.setPackageId(fullPackageInfo.getPackageId());
		databaseHalf.setPackageLimit(fullPackageInfo.getPackageLimit());
		databaseHalf.setPackageName(fullPackageInfo.getPackageName());
		databaseHalf.setPackageType(fullPackageInfo.getPackageType());
		databaseHalf.setUserId(fullPackageInfo.getUserId());
		databaseHalf.setVisibility(fullPackageInfo.isVisible());
		return databaseHalf;
	}
	
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public int getUsedAmount() {
		return usedAmount;
	}
	public void setUsedAmount(int usedAmount) {
		this.usedAmount = usedAmount;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
