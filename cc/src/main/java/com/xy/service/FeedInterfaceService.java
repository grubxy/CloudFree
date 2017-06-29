package com.xy.service;

import com.xy.model.feed.FeedInfoModel;

public interface FeedInterfaceService {

	void getRss();
	
	FeedInfoModel getFeed(String time);
}
