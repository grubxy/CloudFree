package com.xy.utils;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastDFSUtil {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private TrackerClient tracker;
	
	private TrackerServer trackerServer;
	
	public FastDFSUtil(String conf) throws Exception {
		
		ClientGlobal.init(conf);
		
		this.tracker = new TrackerClient();
		
		this.trackerServer =  tracker.getConnection();
		
	}
	
	public String uploadFile(byte[] buffer, String extName) throws IOException, MyException {
		
		StorageServer storageServer = null;
		
		StorageClient client = new StorageClient(trackerServer, storageServer);
		
		String fileId[] = client.upload_file(buffer, extName, null);		
		log.info("uploaded file_id: " + fileId[1] + " groupid: " +  fileId[0]);	
		return ("https://106.14.155.48:443" + "/" + fileId[1]);
	}	
	
	public String deleteFile(String url) {
		return "";
	}
}
