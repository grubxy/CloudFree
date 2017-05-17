package com.xy.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xy.info.Scale;
import com.xy.service.ImgInterfaceService;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Service("ThumbnailatorServiceImpl")
public class ThumbnailatorServiceImpl implements ImgInterfaceService{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public String scale(Scale scale) {
		
		log.info("scale info---->" 
				+ " rate: " + scale.getRate()
				+ " height: " + scale.getHeight()
				+ " width: " + scale.getWidth()
				+ " type: " + scale.getType());
		
		try {
			// 生成图像临时文件
			String rootPath = ".";
			File dir = new File(rootPath + File.separator + "tmpPic");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File serverFile = new File(dir.getAbsolutePath() + File.separator + UUID.randomUUID().toString() + ".jpg");
			log.info("file absolute path:" + serverFile.getAbsolutePath());
			// 写图片文件
			OutputStream out = new FileOutputStream(serverFile);
			byte[] picByte= Base64.decodeBase64(scale.getImageData());
			out.write(picByte);
			out.flush();
			out.close();
			
			//图片缩放
			Thumbnails.of(serverFile).size(Integer.parseInt(scale.getWidth()), Integer.parseInt(scale.getHeight())).toFiles(new File(dir.getAbsolutePath()), Rename.PREFIX_DOT_THUMBNAIL);
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			
		log.info("---------service");		
		return "";
	}
	
}
