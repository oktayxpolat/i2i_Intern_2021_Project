package com.i2iproject.database.models;

import java.math.BigInteger;

class VoltDBPartialPackageInfo{
	private int packageId;
	private int usedAmount;
	private BigInteger startApoch;
	private BigInteger endApoch;
	
	public int getPackageId() {
		return packageId;
	}
	
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	
	public int getUsedAmount() {
		return usedAmount;
	}
	
	public void setUsedAmount(int usedAmount) {
		this.usedAmount = usedAmount;
	}
	
	public BigInteger getStartApoch() {
		return startApoch;
	}
	
	public void setStartApoch(BigInteger startApoch) {
		this.startApoch = startApoch;
	}
	
	public BigInteger getEndApoch() {
		return endApoch;
	}
	
	public void setEndApoch(BigInteger endApoch) {
		this.endApoch = endApoch;
	}
	
}
