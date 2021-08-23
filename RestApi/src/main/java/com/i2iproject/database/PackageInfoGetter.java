package com.i2iproject.database;

import java.util.List;

import com.i2iproject.database.models.FullPackageInfo;
import com.i2iproject.database.models.PackageGetRequestInfo;

public interface PackageInfoGetter {
	public List<FullPackageInfo> getPackageInfo(PackageGetRequestInfo requestInfo);
}
