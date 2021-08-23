package com.i2iproject.database.models;

import java.util.List;

public class VoltDBResponseInfo {
	private String msisdn;
	private List<VoltDBPartialPackageInfo> packagesOfMsisdn;
	
	public String getMsisdn() {
		return msisdn;
	}
	
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	public List<VoltDBPartialPackageInfo> getPackagesOfMsisdn() {
		return packagesOfMsisdn;
	}
	
	public void setPackagesOfMsisdn(List<VoltDBPartialPackageInfo> packagesOfMsisdn) {
		this.packagesOfMsisdn = packagesOfMsisdn;
	}
	
}

