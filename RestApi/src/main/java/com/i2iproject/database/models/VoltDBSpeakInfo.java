package com.i2iproject.database.models;

public class VoltDBSpeakInfo {
	private int partitionKey;
	private String msisdn;
	
	public int getPartitionKey() {
		return partitionKey;
	}
	public void setPartitionKey(int partitionKey) {
		this.partitionKey = partitionKey;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
}
