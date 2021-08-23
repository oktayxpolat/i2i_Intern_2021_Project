package com.i2iproject.databaseimps;

import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.FullPackageInfoReader;
import com.i2iproject.database.models.FullPackageInfo;
import com.i2iproject.databaseimps.utils.ResourceReleaser;

@Component
@Scope("prototype")
public class FullPackageInfoReaderImp implements FullPackageInfoReader{
	private ResourceReleaser resourceReleaser;
	private DataSource dataSource;
	
	private int userId;
	private List<FullPackageInfo> packagesForTheGivenUser;
	private Connection establishedConnection;
	private CallableStatement producedCallableStatement;
	private ResultSet packagesForTheGivenUserTable;
	
	private final int inputUserIdIndex = 1;
	private final int packagesTableForTheGivenUserIndex = 2;
	
	public FullPackageInfoReaderImp(
			ResourceReleaser resourceReleaser,
			DataSource dataSource) {
		this.resourceReleaser = resourceReleaser;
		this.dataSource = dataSource;
	}

	@Override
	public List<FullPackageInfo> readPackagesOfTheUser(int userId) {
		this.userId = userId;
		packagesForTheGivenUser = new ArrayList<>();
		
		try{
			businessLogic();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resourceReleaser.relaseCallableStatement(producedCallableStatement);
			resourceReleaser.relaseConnection(establishedConnection);
		}
		return packagesForTheGivenUser;
	}
	
	private void businessLogic() throws SQLException {
		establishConnection();
		configureDatabaseFullPackageInfoReaderProcedureCall();
		executeConfiguredDatabaseFullPackageInfoReaderProcedure();
		getPackagesForTheGivenUser();
	}
	
	private void establishConnection() throws SQLException {
		/*
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@35.246.237.190:49161:xe","INTERNS","INTERNS");
		*/
		establishedConnection = dataSource.getConnection();
	}
	
	private void configureDatabaseFullPackageInfoReaderProcedureCall() throws SQLException {
		String sqlForCallingProcedure = "{call INTERNS.AUSER_PACKAGE_INFO(?,?)}";
		producedCallableStatement = establishedConnection.prepareCall(sqlForCallingProcedure);
		producedCallableStatement.setInt(inputUserIdIndex, userId);
		producedCallableStatement.registerOutParameter(packagesTableForTheGivenUserIndex, Types.REF_CURSOR);
	}
	
	private void executeConfiguredDatabaseFullPackageInfoReaderProcedure() throws SQLException {
		producedCallableStatement.execute();
	}

	private void getPackagesForTheGivenUser() throws SQLException {
		packagesForTheGivenUserTable = (ResultSet)producedCallableStatement.getObject(packagesTableForTheGivenUserIndex);
		readPackagesFromResultSet();
	}
	
	private void readPackagesFromResultSet() throws SQLException {
		while(packagesForTheGivenUserTable.next()) {
			FullPackageInfo convertedInfo = convertCurrentRowToFullPackageInfo();
			this.packagesForTheGivenUser.add(convertedInfo);
		}
	}

	private FullPackageInfo convertCurrentRowToFullPackageInfo() throws SQLException {
		final int userIdIndex = 1, packageIdIndex = 2, msisdnIndex = 3, packageNameIndex = 4, packageTypeIndex = 5;
		final int packageLimitIndex = 6, businessZoneIndex = 7, visibilityIndex = 8;
		
		FullPackageInfo convertedInfo = new FullPackageInfo();
		
		convertedInfo.setUserId(packagesForTheGivenUserTable.getInt(userIdIndex));
		
		convertedInfo.setPackageId(packagesForTheGivenUserTable.getInt(packageIdIndex));
		
		convertedInfo.setMsisdn(packagesForTheGivenUserTable.getString(msisdnIndex));
		convertedInfo.setPackageName(packagesForTheGivenUserTable.getString(packageNameIndex));
		
		String packageType = packagesForTheGivenUserTable.getString(packageTypeIndex).toLowerCase();
		convertedInfo.setPackageType(packageType);
		
		convertedInfo.setPackageLimit(packagesForTheGivenUserTable.getInt(packageLimitIndex));
		
		convertedInfo.setBusinessZone(packagesForTheGivenUserTable.getString(businessZoneIndex));
		
		final int falseIndicator = 0;
		int visibility = packagesForTheGivenUserTable.getInt(visibilityIndex);
		convertedInfo.setVisible(visibility != falseIndicator ? true: false);
		
		return convertedInfo;
	}
}
