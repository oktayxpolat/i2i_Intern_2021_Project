package com.i2iproject.builders.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Template {
	private List<TemplateData> data;

	public Template() {
		data = new ArrayList<>();
	}
	
	public void addData(TemplateData dataToBeAdded) {
		data.add(dataToBeAdded);
	}

	public List<TemplateData> getData() {
		return data;
	}
	
	
}
