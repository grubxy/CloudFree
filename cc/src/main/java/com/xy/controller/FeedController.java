package com.xy.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xy.service.FeedInterfaceService;

@RestController
public class FeedController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "FeedServiceImpl")
	private FeedInterfaceService FeedService;
	
	@RequestMapping("/getrss")
	public @ResponseBody String getRss() {
		log.info("--->> get rss");
		return "";
	}
}
