package com.i2iproject.database;

import com.i2iproject.database.models.VoltDBResponseInfo;
import com.i2iproject.database.models.VoltDBSpeakInfo;

public interface VoltDBSpeaker {
	public VoltDBResponseInfo speakToVoltDB(VoltDBSpeakInfo infoToSpeakToVoltDB);
}
