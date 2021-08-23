package com.i2iproject.databaseimps;

import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.i2iproject.database.RegisterConfirmer;
import com.i2iproject.databaseimps.utils.ResourceReleaser;
import com.i2iproject.responders.models.ConfirmRegister;

@Component
@Scope("prototype")
public class RegisterConfirmerImp implements RegisterConfirmer{
	private ResourceReleaser resourceReleaser;
	private DataSource dataSource;

	public RegisterConfirmerImp(
			ResourceReleaser resourceReleaser,
			DataSource dataSource) {
		this.resourceReleaser = resourceReleaser;
		this.dataSource = dataSource;
	}

	private final int registerConfirmationIdIndex = 1;
	private final int generatedCodeIndex = 2;
	private final int isMatchedIndex = 3;
	
	private ConfirmRegister registerConfirmationInfo;
	private Connection establishedConnection;
	private CallableStatement producedCallableStatement;
	private boolean isRegistrationCompleted;
	
	@Override
	public boolean confirmRegister(ConfirmRegister registerConfirmationInfo) {
		this.registerConfirmationInfo = registerConfirmationInfo;
		
		try{
			businessLogic();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			resourceReleaser.relaseCallableStatement(producedCallableStatement);
			resourceReleaser.relaseConnection(establishedConnection);
		}
		
		return isRegistrationCompleted;
	}

	private void businessLogic() throws SQLException {
		establishConnection();
		configureDatabaseRegisterConfirmerProcedureCall();
		executeConfiguredDatabaseRegisterConfirmationProcedure();
		figureOutIfRegistrationCompleted();
	}
	
	private void establishConnection() throws SQLException {
		/*
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@35.246.237.190:49161:xe","INTERNS","INTERNS");
		*/
		establishedConnection = dataSource.getConnection();
	}
	
	private void configureDatabaseRegisterConfirmerProcedureCall() throws SQLException {
		String sqlForCallingProcedure = "{call INTERNS.confirm_register(?,?,?)}";
		producedCallableStatement = establishedConnection.prepareCall(sqlForCallingProcedure);
		producedCallableStatement.setInt(registerConfirmationIdIndex, registerConfirmationInfo.getRegisterConfirmationId());
		producedCallableStatement.setString(generatedCodeIndex, registerConfirmationInfo.getCodeReceivedViaEmail());
		producedCallableStatement.registerOutParameter(isMatchedIndex, Types.INTEGER);
	}
	
	private void executeConfiguredDatabaseRegisterConfirmationProcedure() throws SQLException {
		producedCallableStatement.execute();
	}

	private void figureOutIfRegistrationCompleted() throws SQLException {
		int registerId = producedCallableStatement.getInt(isMatchedIndex);
		isRegistrationCompleted = turnIntegerIndicatorToBooleanOne(registerId);
	}
	
	private boolean turnIntegerIndicatorToBooleanOne(int registerId) {
		final int isRegistrationFailure = 0;
		return registerId != isRegistrationFailure ? true: false;
	}
}
