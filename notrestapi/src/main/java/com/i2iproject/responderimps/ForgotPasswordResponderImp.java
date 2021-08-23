package com.i2iproject.responderimps;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.PasswordChangeCodePersister;
import com.i2iproject.database.models.ForgotPasswordPersistenceInfo;
import com.i2iproject.responders.ForgotPasswordResponder;
import com.i2iproject.responders.utils.SHA256RandomCodeGenerator;

@Component
@Scope("prototype")
public class ForgotPasswordResponderImp implements ForgotPasswordResponder{
	private SHA256RandomCodeGenerator randomCodeGenerator;
	private PasswordChangeCodePersister codePersister;
	
	private String generatedRandomCode;
	private String emailToSendTheGeneratedCode;
	private ForgotPasswordPersistenceInfo persistenceArgumant;
	
	public ForgotPasswordResponderImp(
			SHA256RandomCodeGenerator randomCodeGenerator,
			PasswordChangeCodePersister codePersister) {
		this.randomCodeGenerator = randomCodeGenerator;
		this.codePersister = codePersister;
	}
	
	@Override
	public boolean sendPasswordChangeCodeToGivenEmail(String email) {
		this.emailToSendTheGeneratedCode = email;
		generateRandomCodeForPasswordChangeConfirmation();
		produceForgotPasswordPersistenceArgument();
		return codePersister.persistPasswordChangeCode(persistenceArgumant); //check if true, then send mail.
	}
	
	private void generateRandomCodeForPasswordChangeConfirmation() {
		generatedRandomCode = randomCodeGenerator.getRandomSHA256();
	}
	
	private void produceForgotPasswordPersistenceArgument() {
		persistenceArgumant = new ForgotPasswordPersistenceInfo();
		persistenceArgumant.setEmail(emailToSendTheGeneratedCode);
		persistenceArgumant.setGeneratedCode(generatedRandomCode);
	}

}
