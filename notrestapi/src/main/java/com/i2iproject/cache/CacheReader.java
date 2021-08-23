package com.i2iproject.cache;

import java.util.List;

import com.i2iproject.database.models.DatabaseHalfOfPackageInfo;

public interface CacheReader {
	public List<DatabaseHalfOfPackageInfo> getPackages(int userId);
}
