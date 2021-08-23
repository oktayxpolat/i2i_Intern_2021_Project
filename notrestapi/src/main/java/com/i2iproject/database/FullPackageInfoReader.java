package com.i2iproject.database;

import java.util.List;

import com.i2iproject.database.models.FullPackageInfo;

public interface FullPackageInfoReader {
	public List<FullPackageInfo> readPackagesOfTheUser(int userId);
}
