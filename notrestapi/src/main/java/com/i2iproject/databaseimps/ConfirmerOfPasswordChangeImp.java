package com.i2iproject.databaseimps;

import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.ConfirmerOfPasswordChange;
import com.i2iproject.databaseimps.utils.ResourceReleaser;
import com.i2iproject.responders.models.ForgotPasswordConfirm;
import com.i2iproject.responders.utils.SHA256Hasher;

@Component
@Scope("prototype")
public class ConfirmerOfPasswordChangeImp implements ConfirmerOfPasswordChange{
	private ResourceReleaser resourceReleaser;
	private DataSource dataSource;
	private SHA256Hasher hasher;
	
	private ForgotPasswordConfirm passwordChangeConfirmationInfo;
	private Connection establishedConnection;
	private CallableStatement producedCallableStatement;
	private boolean isPasswordSuccefullyChanged;
	
	private final int emailIndex = 1;
	private final int codeReceivedViaEmailIndex = 2;
	private final int newPasswordIndex = 3;
	private final int isPasswordChangeCompletedIndex = 4;
	
	public ConfirmerOfPasswordChangeImp(
			ResourceReleaser resourceReleaser,
			DataSource dataSource,
			SHA256Hasher hasher) {
		this.resourceReleaser = resourceReleaser;
		this.dataSource = dataSource;
		this.hasher = hasher;
	}
	
	@Override
	public boolean confirmPasswordChange(ForgotPasswordConfirm passwordChangeConfirmationInfo) {
		this.passwordChangeConfirmationInfo = passwordChangeConfirmationInfo;
		
		try{
			businessLogic();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resourceReleaser.relaseCallableStatement(producedCallableStatement);
			resourceReleaser.relaseConnection(establishedConnection);
		}
		return isPasswordSuccefullyChanged;
	}
	
	private void businessLogic() throws SQLException {
		establishConnection();
		configureDatabasePasswordChangeConfirmerProcedureCall();
		executeConfiguredDatabasePasswordChangeConfirmerProcedure();
		figureOutIfPasswordHasSuccefullyChanged();
	}
	
	private void establishConnection() throws SQLException  {
		/*
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@35.246.237.190:49161:xe","INTERNS","INTERNS");
		*/
		establishedConnection = dataSource.getConnection();
	}
	
	private void configureDatabasePasswordChangeConfirmerProcedureCall() throws SQLException {
		String sqlForCallingProcedure = "{call INTERNS.confirm_pass_change(?,?,?,?)}";
		CallableStatement callableStatement = establishedConnection.prepareCall(sqlForCallingProcedure);
		callableStatement.setString(emailIndex, passwordChangeConfirmationInfo.getEmail());
		callableStatement.setString(codeReceivedViaEmailIndex, passwordChangeConfirmationInfo.getCodeReceivedViaEmail());
		String hashedPassword = hasher.getSHA256(passwordChangeConfirmationInfo.getPassword());
		callableStatement.setString(newPasswordIndex, hashedPassword);
		callableStatement.registerOutParameter(isPasswordChangeCompletedIndex, Types.INTEGER);
		producedCallableStatement = callableStatement;
	}
	
	private void executeConfiguredDatabasePasswordChangeConfirmerProcedure() throws SQLException {
		producedCallableStatement.execute();
	}
	
	private void figureOutIfPasswordHasSuccefullyChanged() throws SQLException {
		final int codeIsNotPersistedIndicator = 0;
		int isCodePersisted = producedCallableStatement.getInt(isPasswordChangeCompletedIndex);
		isPasswordSuccefullyChanged = isCodePersisted != codeIsNotPersistedIndicator ? true : false;
	}
}
