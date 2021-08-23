package com.i2iproject.cacheimps;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.i2iproject.cache.CacheStructure;
import com.i2iproject.cache.exceptions.CacheDoesNotContainPackagesForMsisdn;
import com.i2iproject.database.ReaderOfAllPackages;
import com.i2iproject.database.models.DatabaseHalfOfPackageInfo;

@Component
public class CacheStructureImp implements CacheStructure{
	private Map<Integer, List<DatabaseHalfOfPackageInfo>> cachedPackages1;
	private Map<Integer, List<DatabaseHalfOfPackageInfo>> cachedPackages2;
	private final ExecutorService executorService;
	private final ReaderOfAllPackages packageReader;
	
	private final int cachedPackages1FirstLevelCacheIndicator = 1;
	private final int cachedPackages2FirstLevelCacheIndicator = 2;
	
	private int whoisFirstLeveLCache = cachedPackages1FirstLevelCacheIndicator;
	private Map<Integer, List<DatabaseHalfOfPackageInfo>> chosenCacheForUpdate;
	
	public CacheStructureImp(ReaderOfAllPackages packageReader) {
		cachedPackages1 = new ConcurrentHashMap<>();
		cachedPackages2 = new ConcurrentHashMap<>();
		executorService = Executors.newSingleThreadExecutor();
		this.packageReader = packageReader;
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				performCacheUpdate();
			}
		});
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
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				performCacheUpdate();
			}
		});
	}
	
	private void performCacheUpdate(){
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
	
	@Override
	public synchronized void writeToCache(List<DatabaseHalfOfPackageInfo> databaseHalfToWrite) {
		final int anyMsisdnIndexOfAnyElement = 0;
		DatabaseHalfOfPackageInfo sampleHalfToExtractMsisdn = databaseHalfToWrite.get(anyMsisdnIndexOfAnyElement);
		if(isCachedPackages1FirstLevelCache()) {
			cachedPackages1.put(sampleHalfToExtractMsisdn.getUserId(), databaseHalfToWrite);
		}
		else
			cachedPackages2.put(sampleHalfToExtractMsisdn.getUserId(), databaseHalfToWrite);
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
	public List<DatabaseHalfOfPackageInfo> getPackages(int userId){
		Map<Integer, List<DatabaseHalfOfPackageInfo>> firstLevelCache = getFirstLevelCache();
		
		List<DatabaseHalfOfPackageInfo> packagesRetrievedFromCache = firstLevelCache.get(userId);
		
		if(!isPackagesRealyRead(packagesRetrievedFromCache))
			throw new CacheDoesNotContainPackagesForMsisdn("CACHE DOES NOT CONTAIN PACKAGES FOR THE USERID:" + userId);
		return packagesRetrievedFromCache;
	}
	
	
	private synchronized Map<Integer, List<DatabaseHalfOfPackageInfo>> getFirstLevelCache(){
		if(isCachedPackages1FirstLevelCache())
			return cachedPackages1;
		else 
			return cachedPackages2;
	}

	
	private boolean isPackagesRealyRead(List<DatabaseHalfOfPackageInfo> packagesRetrievedFromCache) {
		return packagesRetrievedFromCache != null;
	}
	
}
