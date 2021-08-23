package com.i2iproject.database.models;

public class VoltDBSpeakInfo {
	private String msisdn;
	private int partitionKey;
	
	public VoltDBSpeakInfo(String msisdn,int partitionKey) {
		this.msisdn = msisdn;
		this.partitionKey = partitionKey;
	}
	
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
