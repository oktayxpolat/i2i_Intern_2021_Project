package com.i2iproject.responders;

import java.util.List;

import com.i2iproject.responders.models.PackageResponseItem;

public interface PackageInfoResponder {
	public List<PackageResponseItem> getPackageInfoById(int userId);
}
