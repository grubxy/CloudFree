package com.xy.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xy.info.Results;
import com.xy.info.Scale;
import com.xy.service.ImgInterfaceService;

@RestController
public class ImgController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="ThumbnailatorServiceImpl")
	private ImgInterfaceService service;
	
    @RequestMapping("/watermark")
    public @ResponseBody Results Watermark(@RequestBody Scale scale) {
    	Results rs = new Results();
    	log.info("---->/watermark");
    	rs.setErrCode("22");
    	rs.setErrMsg("测试");
    	rs.setUrl("http://www.baidu.com");
    	service.scale(scale);
    	log.info(rs.toString());	
    	return rs; 	
    }
}
