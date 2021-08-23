package com.i2iproject.security.factories;

import com.i2iproject.database.UserLoader;

public interface ProducerOfUserLoader {
	public UserLoader produceUserLoader();	
}
