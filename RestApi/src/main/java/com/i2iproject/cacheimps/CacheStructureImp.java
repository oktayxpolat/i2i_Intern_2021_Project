package com.i2iproject.cacheimps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.i2iproject.cache.CacheStructure;
import com.i2iproject.database.ReaderOfAllPackages;
import com.i2iproject.database.models.DatabaseHalfOfPackageInfo;
import com.i2iproject.database.models.PackageGetRequestInfo;
import com.i2iproject.exceptions.CacheDoesNotContainPackagesForMsisdn;

@Component
public class CacheStructureImp implements CacheStructure,Runnable{
	private Map<String, List<DatabaseHalfOfPackageInfo>> cachedPackages1;
	private Map<String, List<DatabaseHalfOfPackageInfo>> cachedPackages2;
	private final ExecutorService executorService;
	private final ReaderOfAllPackages packageReader;
	
	private final int cachedPackages1FirstLevelCacheIndicator = 1;
	private final int cachedPackages2FirstLevelCacheIndicator = 2;
	
	private int whoisFirstLeveLCache=1;
	private Map<String, List<DatabaseHalfOfPackageInfo>> chosenCacheForUpdate;
	
	public CacheStructureImp(ReaderOfAllPackages packageReader) {
		cachedPackages1 = new HashMap<>();
		cachedPackages2 = new HashMap<>();
		executorService = Executors.newSingleThreadExecutor();
		this.packageReader = packageReader;
		addHookForShuttingThreadPool();
	}
	
	private void addHookForShuttingThreadPool() {
		 Runtime.getRuntime().addShutdownHook(new Thread()
		    {
		      public void run()
		      {
		    	  shutDownCache();
		      }
		    });
	}
	
	@Override
	public void shutDownCache() {
		executorService.shutdownNow();
	}
	
	@Override
	public void askCacheUpdate() {
		executorService.submit(this);
	}
	
	@Override
	public void run() {
		chooseCacheToUpdate();
		updateCache();
	}
	
	private synchronized void chooseCacheToUpdate() {
		if(isCachedPackages1FirstLevelCache())
			chosenCacheForUpdate = cachedPackages2;
		else
			chosenCacheForUpdate = cachedPackages1;
	}
	
	private boolean isCachedPackages1FirstLevelCache() {
		return whoisFirstLeveLCache == cachedPackages1FirstLevelCacheIndicator;
	}
	
	public void updateCache() {
		chosenCacheForUpdate.clear();
		chosenCacheForUpdate.putAll(packageReader.readPackagesForAllMsisdns());
		switchCacheLevels();
	}

	private synchronized void switchCacheLevels() {
		if(isCachedPackages1FirstLevelCache())
			makeCachedPackages2FirstLevelCache();
		else
			makeCachedPackages1FirstLevelCache();
	}
	
	private void makeCachedPackages2FirstLevelCache() {
		whoisFirstLeveLCache = cachedPackages2FirstLevelCacheIndicator;
	}
	
	private void makeCachedPackages1FirstLevelCache() {
		whoisFirstLeveLCache = cachedPackages1FirstLevelCacheIndicator;
	}

	
	@Override
	public List<DatabaseHalfOfPackageInfo> getPackages(PackageGetRequestInfo requestInfo){
		Map<String, List<DatabaseHalfOfPackageInfo>> firstLevelCache = getFirstLevelCache();
		final String msisdn = requestInfo.getMsisdn();
		
		List<DatabaseHalfOfPackageInfo> packagesRetrievedFromCache = firstLevelCache.get(msisdn);
		
		if(!isPackagesRealyRead(packagesRetrievedFromCache))
			throw new CacheDoesNotContainPackagesForMsisdn("CACHE DOES NOT CONTAIN PACKAGES FOR THE MSISDN:" + msisdn);
		return packagesRetrievedFromCache;
	}
	
	private synchronized Map<String, List<DatabaseHalfOfPackageInfo>> getFirstLevelCache(){
		if(isCachedPackages1FirstLevelCache())
			return cachedPackages1;
		else 
			return cachedPackages2;
	}

	
	private boolean isPackagesRealyRead(List<DatabaseHalfOfPackageInfo> packagesRetrievedFromCache) {
		return packagesRetrievedFromCache != null;
	}
	
}
