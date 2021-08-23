package com.i2iproject.controllers.factoryimps;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.i2iproject.controllers.factories.ProducerOfForgotPasswordObjects;
import com.i2iproject.responders.ConfirmPasswordChangeResponder;
import com.i2iproject.responders.ForgotPasswordResponder;

@Component
public class ProducerOfForgotPasswordObjectsImp implements ProducerOfForgotPasswordObjects{
	private ApplicationContext appContext;
	
	public ProducerOfForgotPasswordObjectsImp(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	@Override
	public ForgotPasswordResponder produceForgotPasswordResponder() {
		return appContext.getBean(ForgotPasswordResponder.class);
	}

	@Override
	public ConfirmPasswordChangeResponder produceConfirmerOfForgotPasswordResponder() {
		return appContext.getBean(ConfirmPasswordChangeResponder.class);
	}

}
