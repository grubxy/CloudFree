package com.xy.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		
	@Resource(name = "FeedServiceImpl")
	private FeedInterfaceService FeedService; 
	
	@Scheduled(fixedRate = 300000)
	public void getRssSchedule() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		log.info("task schedule....:", dateFormat.format(new Date()).toString());
		FeedService.getRss();
	}
	
	@Scheduled(fixedRate = 300000)
	public void deleteRssSchedule() {
		// 获取当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dd = new Date();
		log.info("task schedule delete old data....:", df.format(dd).toString());	
		// 2天前时间
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		c.add(Calendar.DATE, -2);
		String time = df.format(c.getTime());
		log.info("delete feed time: " + time);
		FeedService.deleteFeed(time);
	}
}
