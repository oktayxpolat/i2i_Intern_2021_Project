package com.i2iproject.hazelcastimps;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.i2iproject.hazelcast.HazelCastSpeaker;
import com.i2iproject.hazelcast.models.HazelCastResponseInfo;

@Component
@Scope("prototype")
public class HazelCastSpeakerImp implements HazelCastSpeaker {
	private static final String hazelCastAdress = "172.104.149.9:5701";
	private static final String topicName = "Balance";
	
	private static final ClientConfig clientConfig = new ClientConfig();
	private static final Object LockForClient = new Object();
	private static HazelcastInstance client;
	
	private String msisdn;
	private int partitionKeyForTheGivenMsisdn;
	private HazelCastResponseInfo hazelCastResponseInfo;
	
	public HazelCastSpeakerImp() {
		synchronized(LockForClient){
			if(client == null){
			clientConfig.setClusterName("dev");
			clientConfig.setProperty("hazelcast.logging.type", "none");
			clientConfig.getNetworkConfig().addAddress(hazelCastAdress);
			client = HazelcastClient.newHazelcastClient(clientConfig);
			addHookForShuttingHazelcastClient();
			}
	 	}
	}
	
	private void addHookForShuttingHazelcastClient() {
		 Runtime.getRuntime().addShutdownHook(new Thread()
		    {
		      public void run()
		      {
		    	  if(client == null)
						client.shutdown();	
		      }
		    });
	}
	
	@Override
	public HazelCastResponseInfo speakToHazelCast(String msisdn) {
		//int partitionKeyForTheGivenMsisdn=1; Hazelcast "Balance" topici ile çalışmıyor ise VoltDB partition key i 1 yapar.
		//Hazelcast te sorun var isee return partitionKeyForTheGivenMsisdn yap. VoltDB deki kayıtların partition key i 1 olmalı.
	
		this.msisdn = msisdn;
		
		initializeHazelcastClient();
		getPartitionKeyForGivenMsisdnFromHazelCast();
		produceResponse();
		return hazelCastResponseInfo;
	}
	
	private void initializeHazelcastClient() {
		synchronized(LockForClient){
			if(isHazelCastClientClosedForSomeReason()) 
				client = HazelcastClient.newHazelcastClient(clientConfig);
		}
	}
	
	private boolean isHazelCastClientClosedForSomeReason() {
		return client == null;
	}
	
	private void getPartitionKeyForGivenMsisdnFromHazelCast() {
		IMap<String, Integer> msisdnToPartitionKeyMap = client.getMap(topicName);
		partitionKeyForTheGivenMsisdn = msisdnToPartitionKeyMap.get(msisdn);
	}
	
	private void produceResponse() {
		hazelCastResponseInfo = new HazelCastResponseInfo();
		hazelCastResponseInfo.setMsisdn(msisdn);
		hazelCastResponseInfo.setPartitionKey(partitionKeyForTheGivenMsisdn);
	}
	
}
