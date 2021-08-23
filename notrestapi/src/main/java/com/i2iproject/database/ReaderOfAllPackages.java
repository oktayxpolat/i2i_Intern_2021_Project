package com.i2iproject.database;

import java.util.List;
import java.util.Map;

import com.i2iproject.database.models.DatabaseHalfOfPackageInfo;

public interface ReaderOfAllPackages {
	public Map<Integer, List<DatabaseHalfOfPackageInfo>> readPackagesForAllMsisdns();	
}
