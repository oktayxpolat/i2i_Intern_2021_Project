package com.i2iproject.controllers.factoryimps;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.i2iproject.controllers.factories.ProducerOfPackageInfoResponder;
import com.i2iproject.responders.PackageInfoResponder;

@Component
public class ProducerOfPackageInfoResponderImp implements ProducerOfPackageInfoResponder{
	private ApplicationContext appContex;
	
	public ProducerOfPackageInfoResponderImp(ApplicationContext appContex) {
		this.appContex = appContex;
	}

	@Override
	public PackageInfoResponder producePackageInfoResponder() {
		return appContex.getBean(PackageInfoResponder.class);
	}

}
