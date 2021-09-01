package com.i2iproject.databaseimps;

import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.i2iproject.database.ReaderOfAllPackages;
import com.i2iproject.database.models.DatabaseHalfOfPackageInfo;
import com.i2iproject.databaseimps.utils.ResourceReleaser;

@Component
public class ReaderOfAllPackagesImp implements ReaderOfAllPackages{
	private ResourceReleaser resourceReleaser;
	private DataSource dataSource;
	
	private final int packagesForAllUsersTableIndex = 1;
	
	private ThreadLocal<InstanceVariablesForAThread> instanceVariables;
	
	private class InstanceVariablesForAThread{
		public Map<Integer, List<DatabaseHalfOfPackageInfo>> packagesForAllUsers;
		public Connection establishedConnection;
		public CallableStatement producedCallableStatement;
		public ResultSet packagesForAllUsersTable;
		public DatabaseHalfOfPackageInfo filledDatabasePackageInfo;
		public List<DatabaseHalfOfPackageInfo> packagesOfTheGivenUser;
	}
	
	public ReaderOfAllPackagesImp(
			ResourceReleaser resourceReleaser,
			DataSource dataSource) {
		this.resourceReleaser = resourceReleaser;
		this.dataSource = dataSource;
		instanceVariables = new ThreadLocal<>();
	}

	@Override
	public Map<Integer, List<DatabaseHalfOfPackageInfo>> readPackagesForAllMsisdns() {
		instanceVariables.set(new InstanceVariablesForAThread());
		
		instanceVariables.get().packagesForAllUsers = new HashMap<>();
		
		try{
			establishConnection();
			configureDatabaseProcedureCallToReadAllPackages();
			executeConfiguredDatabaseProcedureForReadingAllPackages();
			readPackagesForAllUsers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			resourceReleaser.relaseCallableStatement(instanceVariables.get().producedCallableStatement);
			resourceReleaser.relaseConnection(instanceVariables.get().establishedConnection);
		}
		
		 return getPackagesForAllUsers();
	}

	private void establishConnection() throws SQLException {
		/*
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@35.246.237.190:49161:xe","INTERNS","INTERNS");
		*/
		instanceVariables.get().establishedConnection = dataSource.getConnection();
	}
	
	private void configureDatabaseProcedureCallToReadAllPackages() throws SQLException {
		String sqlForCallingProcedure = "{call INTERNS.all_pack_info(?)}";
		
		instanceVariables.get().producedCallableStatement = 
				instanceVariables.get().establishedConnection.prepareCall(sqlForCallingProcedure);
		instanceVariables.get().producedCallableStatement.registerOutParameter(packagesForAllUsersTableIndex, Types.REF_CURSOR);
	}

	private void executeConfiguredDatabaseProcedureForReadingAllPackages() throws SQLException {
		instanceVariables.get().producedCallableStatement.execute();
	}
	
	private void readPackagesForAllUsers() throws SQLException {
		ResultSet packagesForAllUsersTableForExecutingThread = (ResultSet)
				instanceVariables.get().producedCallableStatement.getObject(packagesForAllUsersTableIndex);
		instanceVariables.get().packagesForAllUsersTable = packagesForAllUsersTableForExecutingThread;
		convertResultSetToMap();
	}
	
	private void convertResultSetToMap() throws SQLException {
		while(instanceVariables.get().packagesForAllUsersTable.next()) {
			instanceVariables.get().filledDatabasePackageInfo = fillRowToDatabaseHalfOfPackageInfo();
			putNewlyCratedPackageInfoToMap();
		}
	}

	private DatabaseHalfOfPackageInfo fillRowToDatabaseHalfOfPackageInfo() throws SQLException {
		final int userIdIndex=1, packageIdIndex = 2, msisdnIndex = 3, packageTypeIndex = 4, packageNameIndex = 5;
		final int packageLimitIndex = 6, businessZoneIndex = 7, visibilityIndex = 8;
		
		DatabaseHalfOfPackageInfo newPackageInfo = new DatabaseHalfOfPackageInfo();
		
		ResultSet packagesForAllUsersTableForExecutingThread = instanceVariables.get().packagesForAllUsersTable;
		
		newPackageInfo.setUserId(packagesForAllUsersTableForExecutingThread.getInt(userIdIndex));
		newPackageInfo.setPackageId(packagesForAllUsersTableForExecutingThread.getInt(packageIdIndex));
		
		newPackageInfo.setMsisdn(packagesForAllUsersTableForExecutingThread.getString(msisdnIndex));
		
		String packageType = packagesForAllUsersTableForExecutingThread.getString(packageTypeIndex).toLowerCase();
		newPackageInfo.setPackageType(packageType);
		
		newPackageInfo.setPackageName(packagesForAllUsersTableForExecutingThread.getString(packageNameIndex));
		
		newPackageInfo.setPackageLimit(packagesForAllUsersTableForExecutingThread.getInt(packageLimitIndex));
		newPackageInfo.setBusinessZone(packagesForAllUsersTableForExecutingThread.getString(businessZoneIndex));
		
		int visibility = packagesForAllUsersTableForExecutingThread.getInt(visibilityIndex);
		boolean visible = turnIntegerToBoolean(visibility);
		newPackageInfo.setVisibility(visible);
		return newPackageInfo;
	}
	
	private boolean turnIntegerToBoolean(int numberRepresentingBoolean) {
		final int falseAsInteger = 0;
		return numberRepresentingBoolean != falseAsInteger ? true: false;
	}
	
	private void putNewlyCratedPackageInfoToMap() {
		Map<Integer, List<DatabaseHalfOfPackageInfo>> packagesForAllUsersForExecutingThread = 
				instanceVariables.get().packagesForAllUsers;
		
		instanceVariables.get().packagesOfTheGivenUser =  
				packagesForAllUsersForExecutingThread.get(instanceVariables.get().filledDatabasePackageInfo.getUserId());
		
		 if(doesPackagesListExistForTheGivenUser(instanceVariables.get().packagesOfTheGivenUser)) {
			 actWhenPackageAlreadyExist();
		 }else {
			 actWhenPackageDoesNotExist();
		 }
	}

	private boolean doesPackagesListExistForTheGivenUser(List<DatabaseHalfOfPackageInfo> packagesOfTheGivenUser) {
		return packagesOfTheGivenUser != null;
	}

	private void actWhenPackageAlreadyExist() {
		instanceVariables.get().packagesOfTheGivenUser.add(instanceVariables.get().filledDatabasePackageInfo);
	}
	
	private void actWhenPackageDoesNotExist() {
		List<DatabaseHalfOfPackageInfo> packagesForTheCurrentUser = new ArrayList<>();
		packagesForTheCurrentUser.add(instanceVariables.get().filledDatabasePackageInfo);
		instanceVariables.get().packagesForAllUsers.put(instanceVariables.get().filledDatabasePackageInfo.getUserId(), packagesForTheCurrentUser);
	}
	
	private  Map<Integer, List<DatabaseHalfOfPackageInfo>> getPackagesForAllUsers(){
		 Map<Integer, List<DatabaseHalfOfPackageInfo>> packagesForAllUsersForExecutingThread = 
				 instanceVariables.get().packagesForAllUsers;
		 instanceVariables.remove();
		 return packagesForAllUsersForExecutingThread;
	}
}
