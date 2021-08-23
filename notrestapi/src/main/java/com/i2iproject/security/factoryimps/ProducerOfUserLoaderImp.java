package com.i2iproject.security.factoryimps;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.i2iproject.database.UserLoader;
import com.i2iproject.security.factories.ProducerOfUserLoader;

@Component
public class ProducerOfUserLoaderImp implements ProducerOfUserLoader{
	private ApplicationContext appContext;
	
	public ProducerOfUserLoaderImp(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	@Override
	public UserLoader produceUserLoader() {
		return appContext.getBean(UserLoader.class);
	}

}
