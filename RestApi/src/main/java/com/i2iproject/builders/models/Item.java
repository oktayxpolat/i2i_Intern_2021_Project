package com.i2iproject.builders.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Item {
	private List<ItemsData> data;

	public Item() {
		data = new ArrayList<>();
	}
	
	public void addData(ItemsData dataToBeAdded) {
		data.add(dataToBeAdded);
	}

	public List<ItemsData> getData() {
		return data;
	}
}
