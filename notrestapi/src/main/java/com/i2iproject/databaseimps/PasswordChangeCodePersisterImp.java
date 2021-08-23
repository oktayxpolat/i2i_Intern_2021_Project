package com.i2iproject.databaseimps;

import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.PasswordChangeCodePersister;
import com.i2iproject.database.models.ForgotPasswordPersistenceInfo;
import com.i2iproject.databaseimps.utils.ResourceReleaser;
import com.i2iproject.responders.utils.EmailSender;

@Component
@Scope("prototype")
public class PasswordChangeCodePersisterImp implements PasswordChangeCodePersister{
	private ResourceReleaser resourceReleaser;
	private EmailSender mailSender;
	private DataSource dataSource;
	
	private ForgotPasswordPersistenceInfo passwordChangePersistenceInfo;
	private Connection establishedConnection;
	private CallableStatement producedCallableStatement;
	private boolean isCodePersisted;
	
	private final int emailIndex = 1;
	private final int generatedCodeIndex = 2;
	private final int isCodePersistedIndex = 3;
	
	public PasswordChangeCodePersisterImp(
			ResourceReleaser resourceReleaser,
			EmailSender mailSender,
			DataSource dataSource) {
		this.resourceReleaser = resourceReleaser;
		this.mailSender = mailSender;
		this.dataSource = dataSource;
	}
	
	@Override
	public boolean persistPasswordChangeCode(ForgotPasswordPersistenceInfo passwordChangePersistenceInfo) {
		this.passwordChangePersistenceInfo = passwordChangePersistenceInfo;

		try{
			businessLogic();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			resourceReleaser.relaseCallableStatement(producedCallableStatement);
			resourceReleaser.relaseConnection(establishedConnection);
		}
		return isCodePersisted;
	}
	
	private void businessLogic() throws SQLException {
		establishConnection();
		configureDatabasePasswordChangeCodePersisterProcedureCall();
		executeConfiguredDatabaseProcedure();
		figureOutIfCodePersisted();
		sendEmailIfTheConditionsAreMet();
	}

	private void establishConnection() throws SQLException{
		/*
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@35.246.237.190:49161:xe","INTERNS","INTERNS");
		*/
		establishedConnection = dataSource.getConnection();
	}
	
	private void configureDatabasePasswordChangeCodePersisterProcedureCall() throws SQLException {
		String sqlForCallingProcedure = "{call INTERNS.persist_pass_change_code(?,?,?)}";
		CallableStatement callableStatement = establishedConnection.prepareCall(sqlForCallingProcedure);
		callableStatement.setString(emailIndex, passwordChangePersistenceInfo.getEmail());
		callableStatement.setString(generatedCodeIndex, passwordChangePersistenceInfo.getGeneratedCode());
		callableStatement.registerOutParameter(isCodePersistedIndex, Types.INTEGER);
		producedCallableStatement = callableStatement;
	}
	
	private void executeConfiguredDatabaseProcedure() throws SQLException {
		producedCallableStatement.execute();
	}
	
	private void figureOutIfCodePersisted() throws SQLException {
		final int codeIsNotPersistedIndicator = 0;
		int isCodePersistedAsInt = producedCallableStatement.getInt(isCodePersistedIndex);
		isCodePersisted = isCodePersistedAsInt != codeIsNotPersistedIndicator ? true : false;
	}
	
	private void mailGonder() {
		mailSender.sendEmailWithText(
				passwordChangePersistenceInfo.getEmail(),
				"PASSWORD CHANGE CODE:" + passwordChangePersistenceInfo.getGeneratedCode(), 
				"CODE FOR PASSWORD CHANGE"
		);
	}
	
	private void sendEmailIfTheConditionsAreMet() {
		if(isCodePersisted)
			mailGonder();
	}
	
}
