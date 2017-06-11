package com.xy.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xy.model.ConstantErrCode;
import com.xy.model.ResultModel;
import com.xy.model.ScaleModel;
import com.xy.service.ImgInterfaceService;
import com.xy.utils.FastDFSUtil;

import net.coobird.thumbnailator.Thumbnails;

@Service("ThumbnailatorServiceImpl")
public class ThumbnailatorServiceImpl implements ImgInterfaceService{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public ResultModel scale(ScaleModel scale) {
		
		ResultModel rs = new ResultModel();
		
		log.info("scale info---->" 
				+ " rate: " + scale.getRate()
				+ " height: " + scale.getHeight()
				+ " width: " + scale.getWidth()
				+ " type: " + scale.getType());
					
		// base64 解码
		byte[] picByte= Base64.decodeBase64(scale.getImageData());
					
		//图片缩放
		ByteArrayOutputStream outPic = new ByteArrayOutputStream();
		try {
			Thumbnails.of(new ByteArrayInputStream(picByte)).size(Integer.parseInt(scale.getWidth()), Integer.parseInt(scale.getHeight())).outputFormat("jpg").toOutputStream(outPic);
		} catch (NumberFormatException e1) {
			rs.setErrCode(ConstantErrCode.IMG_ENGINE_ERROR.getCode());
			rs.setErrMsg(e1.getMessage());
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			rs.setErrCode(ConstantErrCode.IMG_ENGINE_ERROR.getCode());
			rs.setErrMsg(e1.getMessage());
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		// 上传
		try {
		FastDFSUtil uploader = new FastDFSUtil("fdfs_client.conf");
		String url = uploader.uploadFile(outPic.toByteArray(), "jpg");
		log.info("uploader result: " + url);				
		rs.setUrl(url);
		} catch (Exception e) {
			rs.setErrCode(ConstantErrCode.STORAGE_ERROR.getCode());
			rs.setErrMsg(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		log.info("---------service over: " + rs.toString());		
		return rs;
	}
}
