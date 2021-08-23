package com.i2iproject.database;

import java.util.List;

import com.i2iproject.database.models.VoltDBHalfOfThePackageInfo;
import com.i2iproject.database.models.VoltDBSpeakInfo;

public interface VoltDBSpeaker {
	public List<VoltDBHalfOfThePackageInfo> speakToVoltDB(VoltDBSpeakInfo infoToSpeakToVoltDB);
}
