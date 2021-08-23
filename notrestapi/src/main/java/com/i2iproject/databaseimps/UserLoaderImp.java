package com.i2iproject.databaseimps;

import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.UserLoader;
import com.i2iproject.database.models.UserModel;
import com.i2iproject.databaseimps.utils.ResourceReleaser;

@Component
@Scope("prototype")
public class UserLoaderImp implements UserLoader{
	private ResourceReleaser resourceReleaser;
	private DataSource dataSource;
	
	private Connection establishedConnection;
	private CallableStatement producedCallableStatement;
	
	private String incomingMsisdn;
	private UserModel user;
	private String databaseMsisdn;
	private String databasePassword;
	
	private final int msisdnIndex  = 1;
	private final int databaseMsisdnIndex = 2;
	private final int databasePasswordIndex = 3;
	
	public UserLoaderImp(
			ResourceReleaser resourceReleaser,
			DataSource dataSource) {
		this.resourceReleaser = resourceReleaser;
		this.dataSource = dataSource;
	}
	
	@Override
	public UserModel loadUserByPhoneNumber(String msisdn) {
		this.incomingMsisdn = msisdn;
	
		try{
			businessLogic();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			resourceReleaser.relaseCallableStatement(producedCallableStatement);
			resourceReleaser.relaseConnection(establishedConnection);
		}
		return user;
	}
	
	private void businessLogic() throws SQLException {
		establishConnection();
		configureDatabaseUserLoaderProcedureCall();
		executeConfiguredDatabaseUserLoaderProcedure();
		getLoadedUserInfos();
	    createUser();
	}
	
	private void establishConnection() throws SQLException {
		/*
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@35.246.237.190:49161:xe","INTERNS","INTERNS");
		*/
		establishedConnection = dataSource.getConnection();
	}
	
	private void configureDatabaseUserLoaderProcedureCall() throws SQLException {
		String sqlForCallingProcedure = "{call INTERNS.load_user_msisdn(?,?,?)}";
		producedCallableStatement = establishedConnection.prepareCall(sqlForCallingProcedure);
		producedCallableStatement.setString(msisdnIndex, incomingMsisdn);
		producedCallableStatement.registerOutParameter(databaseMsisdnIndex, Types.VARCHAR);
		producedCallableStatement.registerOutParameter(databasePasswordIndex, Types.VARCHAR);
	}
	
	private void executeConfiguredDatabaseUserLoaderProcedure() throws SQLException {
		producedCallableStatement.execute();
	}
	
	private void getLoadedUserInfos() throws SQLException {
		databaseMsisdn = producedCallableStatement.getString(databaseMsisdnIndex);
	    databasePassword = producedCallableStatement.getString(databasePasswordIndex);
	}
	
	private void createUser() {
		user = new UserModel();
		user.setMsidn(databaseMsisdn);
	    user.setPassWord(databasePassword);
	}
}
