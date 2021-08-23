package com.i2iproject.builders.models;

public class TemplateData {
	private String name;
	private String value;
	
	public TemplateData(String name) {
		this.name = name;
	}
	
	public TemplateData(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
	
}
