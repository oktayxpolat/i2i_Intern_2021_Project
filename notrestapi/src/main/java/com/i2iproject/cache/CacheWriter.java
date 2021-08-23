package com.i2iproject.cache;

import java.util.List;

import com.i2iproject.database.models.DatabaseHalfOfPackageInfo;

public interface CacheWriter {
	public void writeToCache(List<DatabaseHalfOfPackageInfo> databaseHalfToWrite);
}
