package com.i2iproject.databaseimps;

import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.UserInfoGetterUsingPartialUserInfo;
import com.i2iproject.database.models.FullUserInfo;
import com.i2iproject.database.models.PartialUserInfo;
import com.i2iproject.databaseimps.utils.ResourceReleaser;

@Component
@Scope("prototype")
public class UserInfoGetterUsingPartialUserInfoImp implements UserInfoGetterUsingPartialUserInfo {
	private ResourceReleaser resourceReleaser;
	private DataSource dataSource;

	private PartialUserInfo partialUserInfo;
	private Connection establishedConnection;
	private CallableStatement producedCallableStatement;
	private FullUserInfo fullUserInfo;
	
	public UserInfoGetterUsingPartialUserInfoImp(
			ResourceReleaser resourceReleaser,
			DataSource dataSource) {
		this.resourceReleaser = resourceReleaser;
		this.dataSource = dataSource;
	}
	
	private final int msisdnIndex  = 1;
	private final int hashedPasswordIndex = 2;
	private final int databaseUserIdIndex = 3;
	private final int databaseNameIndex = 4;
	private final int databaseLastNameIndex = 5;
	private final int databaseEmailIndex = 6;
	private final int databaseMsisdnIndex = 7;

	
	@Override
	public FullUserInfo getFullUserInfoFromPartialInfo(PartialUserInfo partialUserInfo) {
		this.partialUserInfo = partialUserInfo;
		
		try{
			businessLogic();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resourceReleaser.relaseCallableStatement(producedCallableStatement);
			resourceReleaser.relaseConnection(establishedConnection);
		}
		return fullUserInfo;
	}

	private void businessLogic() throws SQLException {
		establishConnection();
		configureDatabaseUserInfoGetterProcedure();
		executeConfiguredDatabaseUserInfoGetterProcedure();
		createFullUserInfo();
	}
	
	private void establishConnection() throws SQLException {
		/*
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@35.246.237.190:49161:xe","INTERNS","INTERNS");
		*/
		establishedConnection = dataSource.getConnection();
	}
	
	private void configureDatabaseUserInfoGetterProcedure() throws SQLException {
		String sqlForCallingProcedure = "{call INTERNS.user_info_msisdn_pass(?,?,?,?,?,?,?)}";
		producedCallableStatement = establishedConnection.prepareCall(sqlForCallingProcedure);
		producedCallableStatement.setString(msisdnIndex, partialUserInfo.getMsisdn());
		producedCallableStatement.setString(hashedPasswordIndex, partialUserInfo.getPassword());
		producedCallableStatement.registerOutParameter(databaseUserIdIndex, Types.INTEGER);
		producedCallableStatement.registerOutParameter(databaseNameIndex, Types.VARCHAR);
		producedCallableStatement.registerOutParameter(databaseLastNameIndex, Types.VARCHAR);
		producedCallableStatement.registerOutParameter(databaseEmailIndex, Types.VARCHAR);
		producedCallableStatement.registerOutParameter(databaseMsisdnIndex, Types.VARCHAR);
	}
	
	private void executeConfiguredDatabaseUserInfoGetterProcedure() throws SQLException {
		producedCallableStatement.execute();
	}
	
	private void createFullUserInfo() throws SQLException {
		fullUserInfo = new FullUserInfo();
		fullUserInfo.setUserId(producedCallableStatement.getInt(databaseUserIdIndex));
		fullUserInfo.setMsisdn(producedCallableStatement.getString(databaseMsisdnIndex));
		fullUserInfo.setName(producedCallableStatement.getString(databaseNameIndex));
		fullUserInfo.setLastName(producedCallableStatement.getString(databaseLastNameIndex));
		fullUserInfo.setEmail(producedCallableStatement.getString(databaseEmailIndex));
	}
	
}
