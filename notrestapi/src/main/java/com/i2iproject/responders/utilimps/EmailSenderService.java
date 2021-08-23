package com.i2iproject.responders.utilimps;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.i2iproject.responders.utils.EmailSender;

@Service
public class EmailSenderService implements EmailSender{
	private JavaMailSender mailSender;
	private final ExecutorService executorService;
	
	private final int numberOfThreadToSendEmail = 2;
	
	EmailSenderService(JavaMailSender mailSender){
		this.mailSender = mailSender;
		this.executorService = Executors.newFixedThreadPool(numberOfThreadToSendEmail);
		addHookForShuttingThreadPool();
	}
	
	private void addHookForShuttingThreadPool() {
		 Runtime.getRuntime().addShutdownHook(new Thread()
		    {
		      public void run()
		      {
		    	  executorService.shutdown();
		      }
		    });
	}
	
	public void sendEmailWithText(
			String toEmail,
			String body,
			String subject
			) {
		executorService.submit(new SendEmailTask(toEmail,body,subject,mailSender));
	}
	
}

class SendEmailTask implements Runnable{
	private String destinationEmail;
	private String body;
	private String subject;
	private JavaMailSender mailSender;
	
	public SendEmailTask(String destinationEmail, String body, String subject,JavaMailSender mailSender) {
		this.destinationEmail = destinationEmail;
		this.body = body;
		this.subject = subject;
		this.mailSender = mailSender;
	}

	@Override
	public void run() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("i2i.project.interns@gmail.com");
		message.setTo(getDestinationEmail());
		message.setText(getBody());
		message.setSubject(getSubject());
		mailSender.send(message);
	}
	
	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}