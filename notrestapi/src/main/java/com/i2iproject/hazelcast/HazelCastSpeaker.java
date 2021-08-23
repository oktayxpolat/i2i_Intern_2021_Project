package com.i2iproject.hazelcast;

import com.i2iproject.hazelcast.models.HazelCastResponseInfo;

public interface HazelCastSpeaker {
	public HazelCastResponseInfo speakToHazelCast(String msisdn);
}
