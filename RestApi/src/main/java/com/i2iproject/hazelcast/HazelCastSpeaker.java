package com.i2iproject.hazelcast;

import com.i2iproject.hazelcast.models.HazelCastResponseInfo;
import com.i2iproject.hazelcast.models.HazelCastSpeakInfo;

public interface HazelCastSpeaker {
	public HazelCastResponseInfo speakToHazelCast(HazelCastSpeakInfo infoToSpeak);
}
