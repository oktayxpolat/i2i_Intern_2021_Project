package com.i2iproject.builders.models;

public class LinkRelation {
	private String rel;
	private String href;
	
	public LinkRelation(String rel, String href) {
		this.rel = rel;
		this.href = href;
	}

	public String getRel() {
		return rel;
	}

	public String getHref() {
		return href;
	}
}
