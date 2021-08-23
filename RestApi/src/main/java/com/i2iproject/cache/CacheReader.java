package com.i2iproject.cache;

import java.util.List;

import com.i2iproject.database.models.DatabaseHalfOfPackageInfo;
import com.i2iproject.database.models.PackageGetRequestInfo;

public interface CacheReader {
	public List<DatabaseHalfOfPackageInfo> getPackages(PackageGetRequestInfo requestInfo);
}
