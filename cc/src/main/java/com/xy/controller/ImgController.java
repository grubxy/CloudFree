package com.xy.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xy.model.ResultModel;
import com.xy.model.ScaleModel;
import com.xy.service.ImgInterfaceService;

@RestController
public class ImgController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="ThumbnailatorServiceImpl")
	private ImgInterfaceService service;
	
    @RequestMapping("/scale")
    public @ResponseBody ResultModel Watermark(@RequestBody ScaleModel scale) {
    	log.info("---->/scale");
    	return service.scale(scale);
    }
}
