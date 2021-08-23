package com.i2iproject.builderImps;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.i2iproject.builders.ItemsBuilder;
import com.i2iproject.builders.models.Item;


@Component
@Scope("prototype")
public class ItemsBuilderImp implements ItemsBuilder{
	private List<Item> itemsToBuild;
	
	public ItemsBuilderImp() {
		this.itemsToBuild = new ArrayList<>();
	}

	@Override
	public void addItem(Item dataToBeAdded) {
		itemsToBuild.add(dataToBeAdded);
		
	}

	@Override
	public List<Item> getConstructedItems() {
		return itemsToBuild;
	}

}
