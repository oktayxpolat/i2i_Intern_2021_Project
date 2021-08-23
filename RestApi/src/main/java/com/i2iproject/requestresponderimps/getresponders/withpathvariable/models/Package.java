package com.i2iproject.requestresponderimps.getresponders.withpathvariable.models;

import java.util.ArrayList;
import java.util.List;

import com.i2iproject.builders.models.ItemsData;

public class Package {
	private List<ItemsData> packageInfos;

	public Package() {
		this.packageInfos = new ArrayList<>();
	}

	public List<ItemsData> getPackageInfos() {
		return packageInfos;
	}
	
	public void addPackageItem(ItemsData packageItem) {
		packageInfos.add(packageItem);
	}
	
}
