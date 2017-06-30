package com.xy.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xy.model.feed.FeedInfoModel;
import com.xy.service.FeedInterfaceService;

@RestController
public class FeedController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "FeedServiceImpl")
	private FeedInterfaceService FeedService;
	
	@RequestMapping(value = "/getfeed")
	public @ResponseBody List<FeedInfoModel> getFeed(@RequestParam(value="time") String time
			, @RequestParam(value="type") String type) {
		log.info("------>/getfeed");
		log.info("time : " + time
				+ "type : " + type);
		return FeedService.getFeed(time);
	}
}
