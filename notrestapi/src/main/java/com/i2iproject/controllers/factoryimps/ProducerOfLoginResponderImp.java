package com.i2iproject.controllers.factoryimps;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.i2iproject.controllers.factories.ProducerOfLoginResponder;
import com.i2iproject.responders.LoginResponder;

@Component
public class ProducerOfLoginResponderImp implements ProducerOfLoginResponder{
	private ApplicationContext appContext;
	
	public ProducerOfLoginResponderImp(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	
	@Override
	public LoginResponder produceLoginResponder() {
		return appContext.getBean(LoginResponder.class);
	}

}
