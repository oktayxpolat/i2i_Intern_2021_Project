package com.i2iproject.databaseimps;

import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.RegisterCodePersister;
import com.i2iproject.database.models.RegisterConfirmationId;
import com.i2iproject.databaseimps.utils.ResourceReleaser;
import com.i2iproject.responders.models.Register;
import com.i2iproject.responders.utils.EmailSender;
import com.i2iproject.responders.utils.SHA256RandomCodeGenerator;

@Component
@Scope("prototype")
public class RegisterCodePersisterImp implements RegisterCodePersister{
	private EmailSender mailSender;
	private SHA256RandomCodeGenerator randomCodeGenerator;
	private ResourceReleaser resourceReleaser;
	private DataSource dataSource;
	
	private Register registerRequestInfo;
	private String generatedCodeForRegistrationRequest;
	private RegisterConfirmationId registerConfirmationId;
	private Connection establishedConnection;
	private CallableStatement producedCallableStatement;
	
	private final int nameIndex = 1;
	private final int lastNameIndex = 2;
	private final int mailIndex = 3;
	private final int msisdnIndex = 4;
	private final int passwordIndex = 5;
	private final int generatedCodeIndex = 6;
	private final int registerConfirmationIdIndex = 7;
	
	public RegisterCodePersisterImp(
			EmailSender mailSender,
			SHA256RandomCodeGenerator randomCodeGenerator,
			ResourceReleaser resourceReleaser,
			DataSource dataSource) {
		this.mailSender = mailSender;
		this.randomCodeGenerator = randomCodeGenerator;
		this.resourceReleaser = resourceReleaser;
		this.dataSource = dataSource;
	}

	@Override
	public RegisterConfirmationId persistRegisterRequest(Register registerRequestInfo) {
		this.registerRequestInfo = registerRequestInfo;
		try{
			businessLogic();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			resourceReleaser.relaseCallableStatement(producedCallableStatement);
			resourceReleaser.relaseConnection(establishedConnection);
		}
		return registerConfirmationId;
	}
	
	private void businessLogic() throws SQLException {
		generateRandomCodeForRegisterRequest();
		persistRequestWithGeneratedCodeAdded();
	}
	
	private void generateRandomCodeForRegisterRequest() {
		generatedCodeForRegistrationRequest = randomCodeGenerator.getRandomSHA256();
	}
	
	private void persistRequestWithGeneratedCodeAdded() throws SQLException {
		initializeRegisterConfirmationId();
		establishConnection();
		configureDatabaseRegisterRequestPersistenceProcedureCall();
		callConfigureDatabaseRegisterRequestPersistenceProcedure();
		writeReturnedConfirmationIdToResponse();
		sendRegistrationRequestCodeToEmail();
	}

	private void initializeRegisterConfirmationId() {
		registerConfirmationId = new RegisterConfirmationId();
		registerConfirmationId.setRegisterConfirmationId(RegisterCodePersister.persistingRegisterCodeFails);
	}

	private void establishConnection() throws SQLException {
		/*
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@35.246.237.190:49161:xe","INTERNS","INTERNS");
		*/
		establishedConnection = dataSource.getConnection();
	}
	
	private void configureDatabaseRegisterRequestPersistenceProcedureCall() throws SQLException {
		String sqlForCallingProcedure = "{call INTERNS.reg_request(?,?,?,?,?,?,?)}";
		producedCallableStatement = establishedConnection.prepareCall(sqlForCallingProcedure);
		producedCallableStatement.setString(nameIndex, registerRequestInfo.getName());
		producedCallableStatement.setString(lastNameIndex, registerRequestInfo.getLastName());
		producedCallableStatement.setString(mailIndex, registerRequestInfo.getEmail());
		producedCallableStatement.setString(msisdnIndex, registerRequestInfo.getMsisdn());
		producedCallableStatement.setString(passwordIndex, registerRequestInfo.getPassword());
		producedCallableStatement.setString(generatedCodeIndex, generatedCodeForRegistrationRequest);
		producedCallableStatement.registerOutParameter(registerConfirmationIdIndex, Types.INTEGER);
	}
	
	private void callConfigureDatabaseRegisterRequestPersistenceProcedure() throws SQLException {
		producedCallableStatement.execute();
	}
	
	private void sendRegistrationRequestCodeToEmail() {
		mailSender.sendEmailWithText(
			registerRequestInfo.getEmail(),
			"REGISTRATION CODE:" + generatedCodeForRegistrationRequest, 
			"CODE FOR REGISTRATION"
		);
	}

	private void writeReturnedConfirmationIdToResponse() throws SQLException {
		int registerId = producedCallableStatement.getInt(registerConfirmationIdIndex);
		registerConfirmationId.setRegisterConfirmationId(registerId);
	}
}
