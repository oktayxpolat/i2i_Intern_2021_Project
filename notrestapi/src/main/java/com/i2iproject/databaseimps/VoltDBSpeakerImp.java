package com.i2iproject.databaseimps;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.voltdb.VoltTable;
import org.voltdb.VoltTableRow;
import org.voltdb.VoltType;
import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;

import com.i2iproject.database.VoltDBSpeaker;
import com.i2iproject.database.models.VoltDBHalfOfThePackageInfo;
import com.i2iproject.database.models.VoltDBSpeakInfo;

@Component
@Scope("prototype")
public class VoltDBSpeakerImp implements VoltDBSpeaker{
	private static final String voltDBIp = "34.107.82.147";
	private static final int voltDBPort = 21212;
	private static final String procedureNameToCall = "GetUserAmount";
	
	private static final ClientConfig config = new ClientConfig("interncell","i2icom");
	private static final Object LockForVoltDBClient = new Object();
	private static Client voltDBClient;
	
	private List<VoltDBHalfOfThePackageInfo> returnVoltDBPackageList;
	private ClientResponse clientResponse;
	private VoltDBSpeakInfo infoToSpeakToVoltDB;
	
	public VoltDBSpeakerImp(){
		synchronized(LockForVoltDBClient){
			if(voltDBClient == null){
				voltDBClient = ClientFactory.createClient(config);
				addHookForShuttingVoltDBClient();
			}
		}
	}
	
	private void addHookForShuttingVoltDBClient() {
		 Runtime.getRuntime().addShutdownHook(new Thread()
		    {
		      public void run()
		      {
		    	  if(voltDBClient == null)
					try {
						voltDBClient.close();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		      }
		    });
	}
	
	@Override
	public List<VoltDBHalfOfThePackageInfo> speakToVoltDB(VoltDBSpeakInfo infoToSpeakToVoltDB) {
		this.infoToSpeakToVoltDB = infoToSpeakToVoltDB;
		
		initializeVoltDBClient();
		
		returnVoltDBPackageList = new ArrayList<>();
		
		try {
			businessLogic();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ProcCallException e) {
			e.printStackTrace();
		}
		return returnVoltDBPackageList;
	}

	private void initializeVoltDBClient() {
		synchronized(LockForVoltDBClient){
			if(isVoltDBClientShutDownForSomeReason())
				voltDBClient = ClientFactory.createClient(config);
		}
	}
	
	private void businessLogic() throws NoConnectionsException, IOException, ProcCallException {
		callVoltDBProcedure();
		produceVoltDBList();
	}
	
	private boolean isVoltDBClientShutDownForSomeReason() {
		return voltDBClient == null;
	}
	
	private void callVoltDBProcedure() throws NoConnectionsException, IOException, ProcCallException {
		voltDBClient.createConnection(voltDBIp, voltDBPort);
		clientResponse = voltDBClient.callProcedure(
				procedureNameToCall,
				infoToSpeakToVoltDB.getPartitionKey(), 
				infoToSpeakToVoltDB.getMsisdn()
		);
	}
	
	private void produceVoltDBList() {
		final int customerInformationTableIndex = 0;
		VoltTable result = clientResponse.getResults()[customerInformationTableIndex];
		for(int currentRowIndex = 0; currentRowIndex < result.getRowCount(); currentRowIndex++) {
			VoltTableRow currentRow = result.fetchRow(currentRowIndex);
			VoltDBHalfOfThePackageInfo newlyCreatedVoltDBPackageHalf = createVoltDBHalfOfPackageInfoOutOfCurrentRow(currentRow);
			returnVoltDBPackageList.add(newlyCreatedVoltDBPackageHalf);
		}
	}

	private VoltDBHalfOfThePackageInfo createVoltDBHalfOfPackageInfoOutOfCurrentRow(VoltTableRow currentRow) {
		VoltDBHalfOfThePackageInfo newlyCreatedVoltDBPackageHalf = new VoltDBHalfOfThePackageInfo();
		
		newlyCreatedVoltDBPackageHalf.setMsisdn((String) currentRow.get("MSISDN", VoltType.STRING));
		newlyCreatedVoltDBPackageHalf.setPackageId((int)currentRow.get("PACKAGE_ID", VoltType.INTEGER));
		newlyCreatedVoltDBPackageHalf.setUsedAmount((Integer)currentRow.get("USED_AMOUNT", VoltType.INTEGER));
		
		Long startDateTimeEpoch = (Long)currentRow.get("START_EPOCH", VoltType.BIGINT);
		LocalDateTime startDateTime = convertEpochToLocalDateTime(startDateTimeEpoch);
		newlyCreatedVoltDBPackageHalf.setStartDate(startDateTime);
		
		Long endDateTimeEpoch = (Long)currentRow.get("END_EPOCH", VoltType.BIGINT);
		LocalDateTime endDateTime = convertEpochToLocalDateTime(endDateTimeEpoch);
		newlyCreatedVoltDBPackageHalf.setEndDate(endDateTime);
		return newlyCreatedVoltDBPackageHalf;
	}
	
	private LocalDateTime convertEpochToLocalDateTime(Long epoch) {
		return Instant.ofEpochMilli(epoch).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
}
