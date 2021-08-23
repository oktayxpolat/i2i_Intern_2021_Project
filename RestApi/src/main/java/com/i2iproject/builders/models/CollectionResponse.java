package com.i2iproject.builders.models;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@Scope("prototype")
@JsonInclude(Include.NON_NULL)
public class CollectionResponse {
	private String href;
	private String version;
	private List<Item> items;
	private List<LinkRelation> links;
	private Template template;
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public List<LinkRelation> getLinks() {
		return links;
	}
	
	public void setLinks(List<LinkRelation> links) {
		this.links = links;
	}
	
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
}
