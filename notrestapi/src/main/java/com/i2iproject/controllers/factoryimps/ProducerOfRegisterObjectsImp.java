package com.i2iproject.controllers.factoryimps;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.i2iproject.controllers.factories.ProducerOfRegisterObjects;
import com.i2iproject.responders.ConfirmRegisterRequestResponder;
import com.i2iproject.responders.RegisterResponder;

@Component
public class ProducerOfRegisterObjectsImp implements ProducerOfRegisterObjects{
	private ApplicationContext appContext;
	
	public ProducerOfRegisterObjectsImp(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	@Override
	public RegisterResponder produceRegisterResponder() {
		return appContext.getBean(RegisterResponder.class);
	}

	@Override
	public ConfirmRegisterRequestResponder produceRegisterConfirmer() {
		return appContext.getBean(ConfirmRegisterRequestResponder.class);
	}
}
