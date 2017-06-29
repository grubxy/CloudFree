package com.xy.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xy.service.FeedInterfaceService;

@Component
public class ScheduledTask {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	
	@Resource(name = "FeedServiceImpl")
	private FeedInterfaceService FeedService; 
	
	@Scheduled(fixedRate = 300000)
	public void getRssSchedule() {
		log.info("task schedule....:", dateFormat.format(new Date()));
		FeedService.getRss();
	}
}
