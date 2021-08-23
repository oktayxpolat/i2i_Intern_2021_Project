package com.i2iproject.databaseimps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.cache.CacheReader;
import com.i2iproject.cache.CacheUpdater;
import com.i2iproject.cache.CacheWriter;
import com.i2iproject.cache.exceptions.CacheDoesNotContainPackagesForMsisdn;
import com.i2iproject.cache.models.MaximumCacheMissNumber;
import com.i2iproject.database.FullPackageInfoReader;
import com.i2iproject.database.PackageInfoGetter;
import com.i2iproject.database.VoltDBSpeaker;
import com.i2iproject.database.models.DatabaseHalfOfPackageInfo;
import com.i2iproject.database.models.FullPackageInfo;
import com.i2iproject.database.models.PackageGetRequestInfo;
import com.i2iproject.database.models.VoltDBHalfOfThePackageInfo;
import com.i2iproject.database.models.VoltDBSpeakInfo;
import com.i2iproject.hazelcast.HazelCastSpeaker;
import com.i2iproject.hazelcast.models.HazelCastResponseInfo;

@Component
@Scope("prototype")
public class PackageInfoGetterImp implements PackageInfoGetter{
	private final CacheReader cacheReader;
	private final CacheUpdater cacheUpdater;
	private final CacheWriter cacheWriter;
	private FullPackageInfoReader packageInfoReader;
	private VoltDBSpeaker voltDBSpeaker;
	private HazelCastSpeaker hazelCastSpeaker;
	private final MaximumCacheMissNumber maximumCacheMissNumber;
	private final AtomicInteger numberOfCacheMiss;
	
	private PackageGetRequestInfo requestInfo;
	private List<DatabaseHalfOfPackageInfo> databaseHalf;
	private Map<String,DatabaseHalfOfPackageInfo> databaseHalfMap;

	private String msisdnUsedForHazelCastForGettingPartitionKey;
	private int partitionKeyUsedInVoltDB;
	private List<VoltDBHalfOfThePackageInfo> voltDBHalf;
	private Map<String, VoltDBHalfOfThePackageInfo> voltDBHalfMap;
	
	private List<DatabaseHalfOfPackageInfo> extractedDatabaseHalfList;
	
	private List<FullPackageInfo> packagesToReturn;
	
	public PackageInfoGetterImp(
			CacheReader cacheReader, 
			CacheUpdater cacheUpdater, 
			CacheWriter cacheWriter,
			FullPackageInfoReader packageInfoReader,
			VoltDBSpeaker voltDBSpeaker,
			HazelCastSpeaker hazelCastSpeaker,
			MaximumCacheMissNumber maximumCachMissNumber) {
		this.cacheReader = cacheReader;
		this.cacheUpdater = cacheUpdater;
		this.cacheWriter = cacheWriter;
		this.packageInfoReader = packageInfoReader;
		this.voltDBSpeaker = voltDBSpeaker;
		this.hazelCastSpeaker = hazelCastSpeaker;
		this.maximumCacheMissNumber = maximumCachMissNumber;
		this.numberOfCacheMiss = new AtomicInteger();
	}

	@Override
	public List<FullPackageInfo> getPackageInfo(PackageGetRequestInfo requestInfo) {
		this.requestInfo = requestInfo;
		
		boolean isCacheFailed = false;
		try {
			databaseHalf = cacheReader.getPackages(requestInfo.getUserId());
		}catch(CacheDoesNotContainPackagesForMsisdn exception) {
			isCacheFailed = true;
		}
		
		if(!isCacheFailed) 
			actWhenCacheSuccess();
		else
			actWhenCacheFails();
		
		return packagesToReturn;
	}
	
	private void actWhenCacheFails() {
		askCacheUpdate();
		getPackageInfoFromDatabase();
	}
	
	private void askCacheUpdate() {
		if(isChacheMissAtMaximum())
			cacheUpdater.askCacheUpdate();
	}
	
	private boolean isChacheMissAtMaximum() {
		return numberOfCacheMiss.incrementAndGet() == maximumCacheMissNumber.getMaximumCacheMissNumber();
	}
	
	private void getPackageInfoFromDatabase(){
		readFullPackageListFromTheDatabase();
		extractDatabaseHalfListFromFullPackageInfoList();
		addDatabaseHalfListWhichIsReadFromDatabaseToCache();
	}
	
	private void readFullPackageListFromTheDatabase() {
		packagesToReturn = packageInfoReader.readPackagesOfTheUser(requestInfo.getUserId());
	}
	
	private void extractDatabaseHalfListFromFullPackageInfoList() {
		extractedDatabaseHalfList = 
				FullPackageInfo.extractDatabaseHalfListFromFullPackageInfoList(packagesToReturn);
	}
	
	private void addDatabaseHalfListWhichIsReadFromDatabaseToCache() {
		if(!extractedDatabaseHalfList.isEmpty())
			cacheWriter.writeToCache(extractedDatabaseHalfList);
	}
	
	
	private void actWhenCacheSuccess() {
		insertDatabaseHalfOFPackageInfoToMap(); //for effectively combining into fullpackage info
		getPartitionKeyFromHazelCast();
		getVoltDBHalfPackageInfos();
		insertVoltDBHalfOfPackageInfoToMap(); //for effectively combining into fullpackage info
		combineBothHalfs();
	}
	
	private void insertDatabaseHalfOFPackageInfoToMap() {
		databaseHalfMap = new HashMap<>();
		String currentMsisdn = null;
		for(DatabaseHalfOfPackageInfo currentDatabaseHalf: databaseHalf) {
			currentMsisdn = currentDatabaseHalf.getMsisdn();
			int currentPackageId = currentDatabaseHalf.getPackageId();
			databaseHalfMap.put(
					currentMsisdn + currentPackageId, 
					currentDatabaseHalf);
		}
		msisdnUsedForHazelCastForGettingPartitionKey = currentMsisdn; //all msisdn are equal. 
	}
	
	private void getPartitionKeyFromHazelCast() {
		HazelCastResponseInfo hazelCastResponse = hazelCastSpeaker.speakToHazelCast(msisdnUsedForHazelCastForGettingPartitionKey);
		partitionKeyUsedInVoltDB = hazelCastResponse.getPartitionKey();
	}
	
	private void getVoltDBHalfPackageInfos() {
		voltDBHalf = voltDBSpeaker.speakToVoltDB(new VoltDBSpeakInfo(msisdnUsedForHazelCastForGettingPartitionKey,partitionKeyUsedInVoltDB));
	}

	private void insertVoltDBHalfOfPackageInfoToMap() {
		voltDBHalfMap = new HashMap<>();
		String currentMsisdn = null;
		for(VoltDBHalfOfThePackageInfo currentVoltDBHalf: voltDBHalf) {
			currentMsisdn = currentVoltDBHalf.getMsisdn();
			int currentPackageId = currentVoltDBHalf.getPackageId();
			voltDBHalfMap.put(
					currentMsisdn + currentPackageId, 
					currentVoltDBHalf);
		}
	}
	
	private void combineBothHalfs() {
		packagesToReturn = new ArrayList<>();
		
		FullPackageInfo combinedPackageInfo=null;;
		for(String currentKey: databaseHalfMap.keySet()) {
			DatabaseHalfOfPackageInfo databaseHalfInfo = databaseHalfMap.get(currentKey);
			VoltDBHalfOfThePackageInfo voltDBHalfInfo = voltDBHalfMap.get(currentKey);
			if(isPartsOfPackageInfoFitForCombination(databaseHalfInfo,voltDBHalfInfo)) {
				combinedPackageInfo = FullPackageInfo.combineDatabaseHalfAndVoltDBHalf(databaseHalfInfo, voltDBHalfInfo);
				packagesToReturn.add(combinedPackageInfo);
			}
		}
	}
	
	private boolean isPartsOfPackageInfoFitForCombination(
			DatabaseHalfOfPackageInfo databaseHalfInfo,
			VoltDBHalfOfThePackageInfo voltDBHalfInfo) {
		return databaseHalfInfo != null && voltDBHalfInfo != null;
	}
	
}
