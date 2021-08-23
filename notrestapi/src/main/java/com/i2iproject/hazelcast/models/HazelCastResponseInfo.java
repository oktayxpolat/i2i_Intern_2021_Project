package com.i2iproject.hazelcast.models;

public class HazelCastResponseInfo {
	private String msisdn;
	private int partitionKey;
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public int getPartitionKey() {
		return partitionKey;
	}
	public void setPartitionKey(int partitionKey) {
		this.partitionKey = partitionKey;
	}
	
	
}
