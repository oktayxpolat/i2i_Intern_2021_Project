package com.i2iproject.cache.models;

import org.springframework.stereotype.Component;

@Component
public class MaximumCacheMissNumber {
	private int maximumCacheMissNumber = 10;

	public synchronized int getMaximumCacheMissNumber() {
		return maximumCacheMissNumber;
	}

	public synchronized void setMaximumCacheMissNumber(int maximumCacheMissNumber) {
		this.maximumCacheMissNumber = maximumCacheMissNumber;
	}

}
