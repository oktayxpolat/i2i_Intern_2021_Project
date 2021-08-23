package com.i2iproject.builderImps;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.builders.TemplateBuilder;
import com.i2iproject.builders.models.Template;
import com.i2iproject.builders.models.TemplateData;

@Component
@Scope("prototype")
public class TemplateBuilderImp implements TemplateBuilder{
	private Template templateToBuild;
		
	public TemplateBuilderImp(Template templateToBuild) {
		this.templateToBuild = templateToBuild;
	}

	public void addTemplateData(TemplateData dataToBeAdded) {
		templateToBuild.addData(dataToBeAdded);
	}
	
	public Template getConstructedTemplate() {
		return this.templateToBuild;
	}
	
}
