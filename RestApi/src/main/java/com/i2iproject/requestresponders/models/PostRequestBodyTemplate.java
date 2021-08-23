package com.i2iproject.requestresponders.models;

import java.util.ArrayList;
import java.util.List;

public class PostRequestBodyTemplate {
	private List<DataElement> data;
	
	public PostRequestBodyTemplate(){
		data = new ArrayList<DataElement>();
	}

	public List<DataElement> getData() {
		return data;
	}

	public void setData(List<DataElement> data) {
		this.data = data;
	}
	
	public void templateDataEkle(DataElement dataElement) {
		data.add(dataElement);
	}
}
