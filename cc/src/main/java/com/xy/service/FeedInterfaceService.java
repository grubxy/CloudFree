package com.xy.service;

import java.util.List;

import com.xy.model.feed.FeedInfoModel;

public interface FeedInterfaceService {

	void getRss();
	
	List<FeedInfoModel> getFeed(String time);
}
