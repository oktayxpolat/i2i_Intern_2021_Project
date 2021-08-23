package com.i2iproject.databaseimps;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.i2iproject.cache.CacheReader;
import com.i2iproject.cache.CacheUpdater;
import com.i2iproject.cache.models.MaximumCacheMissNumber;
import com.i2iproject.database.PackageInfoGetter;
import com.i2iproject.database.models.DatabaseHalfOfPackageInfo;
import com.i2iproject.database.models.FullPackageInfo;
import com.i2iproject.database.models.PackageGetRequestInfo;
import com.i2iproject.exceptions.CacheDoesNotContainPackagesForMsisdn;

@Component
public class PackageInfoGetterImp implements PackageInfoGetter{
	private final CacheReader cacheReader;
	private final CacheUpdater cacheUpdater;
	private final AtomicInteger numberOfCacheMiss;
	private final MaximumCacheMissNumber maximumCacheMissNumber;
	
	public PackageInfoGetterImp(CacheReader cacheReader, CacheUpdater cacheUpdater, MaximumCacheMissNumber maximumCachMissNumber) {
		this.cacheReader = cacheReader;
		this.cacheUpdater = cacheUpdater;
		this.maximumCacheMissNumber = maximumCachMissNumber;
		this.numberOfCacheMiss = new AtomicInteger();
	}

	@Override
	public List<FullPackageInfo> getPackageInfo(PackageGetRequestInfo requestInfo) {
		List<DatabaseHalfOfPackageInfo> packagesRetrievedFromCache=null;
		List<FullPackageInfo> packagesToReturn = null;
		
		boolean isCacheFailed = false;
		try {
			packagesRetrievedFromCache = cacheReader.getPackages(requestInfo);
		}catch(CacheDoesNotContainPackagesForMsisdn exception) {
			isCacheFailed = true;
		}
		
		if(isCacheFailed) {
			if(isChacheMissAtMaximum())
				cacheUpdater.askCacheUpdate();
			return getPackageInfoFromDatabase(requestInfo);
		}
		//user id geliyor arayüzden ona göre caschle. 
		//sonra msisdn alırsın.
			
		return packagesToReturn;
		
		
		
	}
	
	private List<FullPackageInfo> getPackageInfoFromDatabase(PackageGetRequestInfo requestInfo){
		return null;
	}
	
	
	
	private boolean isChacheMissAtMaximum() {
		return numberOfCacheMiss.incrementAndGet() == maximumCacheMissNumber.getMaximumCacheMissNumber();
	}
	
}
