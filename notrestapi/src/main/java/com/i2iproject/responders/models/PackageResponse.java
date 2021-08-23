package com.i2iproject.responders.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PackageResponse {
	private List<PackageResponseItem> packages;

	public PackageResponse() {
		packages = new ArrayList<>();
	}
	
	public List<PackageResponseItem> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageResponseItem> packages) {
		this.packages = packages;
	}
	
	public void addPackageItem(PackageResponseItem itemToAdd) {
		packages.add(itemToAdd);
	}
	
	
}
