package com.i2iproject.builders;

import java.util.List;
import com.i2iproject.builders.models.Item;

public interface ItemsBuilder {
	public void addItem(Item dataToBeAdded);
	public List<Item> getConstructedItems();
}
